package camellia.ecommerce.payment_service.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import camellia.ecommerce.payment_service.entities.Payment;
import camellia.ecommerce.payment_service.enums.PaymentStatus;
import camellia.ecommerce.payment_service.kafka.Topic;
import camellia.ecommerce.payment_service.kafka.events.InventoryEvent;
import camellia.ecommerce.payment_service.kafka.events.PaymentEvent;
import camellia.ecommerce.payment_service.mappers.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    public static final String METADATA_ORDER_ID = "orderId";

    public static final String METADATA_PAYMENT_ID = "paymentId";

    private final String OPEN_SESSION_STATUS = "open";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final PaymentCRUDService paymentCRUDService;

    private final PaymentMapper paymentMapper;

    private final StripeClient stripeClient;

    @Value("${stripe.currency}")
    private String currency;

    @KafkaListener(topics = "INVENTORY_RESERVED", containerFactory = "inventoryEventKafkaListenerContainerFactory")
    public void handleInventoryReservedEvent(InventoryEvent event) {
        if (paymentCRUDService.existsForOrder(event.orderId())) {
            log.info("Payment already exists for order {}, ignoring duplicate event", event.orderId());
            return;
        }

        Payment payment = paymentCRUDService.create(event.orderId(), new BigDecimal(event.totalPrice()));
        publish(Topic.PAYMENT_PENDING, payment);
    }

    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        PaymentStatus currentStatus = payment.getStatus();

        if (currentStatus == status) {
            log.info("Payment {} is already {}, nothing to do", payment.getPublicId(), status);
            return;
        }

        if (currentStatus == PaymentStatus.SUCCEEDED || currentStatus == PaymentStatus.FAILED) {
            log.warn("Ignoring {} for payment {} already saved as {}", status, payment.getPublicId(), currentStatus);
            return;
        }

        Payment updatedPayment = paymentCRUDService.updateStatus(payment, status);

        switch (status) {
        case PaymentStatus.SUCCEEDED -> publish(Topic.PAYMENT_SUCCEEDED, updatedPayment);
        case PaymentStatus.FAILED -> publish(Topic.PAYMENT_FAILED, updatedPayment);
        default -> log.info("Payment {} moved to {}", updatedPayment.getPublicId(), status);
        }
    }

    public void publish(Topic topic, Payment payment) {
        PaymentEvent event = paymentMapper.toEvent(payment);

        kafkaTemplate.send(topic.name(), event.paymentId().toString(), event);
        log.info("Published event: " + topic.name());
    }

    public Session createPaymentSession(Payment payment) throws StripeException {
        Session existingSession = findAnOpenSession(payment);

        if (existingSession != null) {
            log.info("Reusing open Stripe session {} for payment {}", existingSession.getId(), payment.getPublicId());
            return existingSession;
        }

        Session session = stripeClient.v1().checkout().sessions().create(buildSessionParams(payment));

        payment.setStripePaymentSessionId(session.getId());
        paymentCRUDService.save(payment);

        log.info("Created Stripe session {} for payment {}", session.getId(), payment.getPublicId());

        return session;
    }

    private Session findAnOpenSession(Payment payment) throws StripeException {
        String sessionId = payment.getStripePaymentSessionId();

        if (sessionId == null) {
            return null;
        }

        Session session = stripeClient.v1().checkout().sessions().retrieve(sessionId);

        return OPEN_SESSION_STATUS.equals(session.getStatus()) ? session : null;
    }

    private SessionCreateParams buildSessionParams(Payment payment) {
        return SessionCreateParams.builder()
                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency("euro")
                                .setUnitAmount(toMinorUnits(payment.getAmount()))
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Order " + payment.getOrderId()).build())
                                .build())
                        .build())
                .setMode(SessionCreateParams.Mode.PAYMENT).setUiMode(SessionCreateParams.UiMode.EMBEDDED_PAGE)
                .setReturnUrl("").putMetadata(METADATA_PAYMENT_ID, payment.getPublicId().toString())
                .putMetadata(METADATA_ORDER_ID, payment.getOrderId().toString()).build();
    }

    private long toMinorUnits(BigDecimal amount) {
        return amount.movePointRight(2).setScale(0, RoundingMode.HALF_UP).longValueExact();
    }

}
