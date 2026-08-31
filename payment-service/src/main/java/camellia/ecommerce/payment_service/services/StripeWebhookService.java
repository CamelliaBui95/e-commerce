package camellia.ecommerce.payment_service.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import camellia.ecommerce.payment_service.entities.Payment;
import camellia.ecommerce.payment_service.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeWebhookService {

    private static final String PAYMENT_STATUS_PAID = "paid";
    private final String STRIPE_PAYMENT_FAILED = "checkout.session.async_payment_failed";
    private final String STRIPE_PAYMENT_SUCCEEDED = "checkout.session.async_payment_succeeded";
    private final String STRIPE_PAYMENT_EXPIRED = "checkout.session.expired";
    private final String STRIPE_PAYMENT_COMPLETED = "checkout.session.completed";

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    private final PaymentCRUDService paymentCRUDService;

    private final PaymentService paymentService;

    public void handle(String payload, String signature) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, signature, webhookSecret);

        switch (event.getType()) {
        case STRIPE_PAYMENT_SUCCEEDED, STRIPE_PAYMENT_COMPLETED -> settleSession(event, PaymentStatus.SUCCEEDED);
        case STRIPE_PAYMENT_FAILED, STRIPE_PAYMENT_EXPIRED -> settleSession(event, PaymentStatus.FAILED);
        default -> log.debug("No handler for Stripe event type {}.", event.getType());
        }
    }

    private void settleSession(Event event, PaymentStatus status) {
        Session session = extractSession(event);

        if (session == null) {
            return;
        }

        Payment payment = resolvePayment(session);

        if (payment == null) {
            return;
        }

        if (session.getPaymentIntent() != null) {
            payment.setStripePaymentIntentId(session.getPaymentIntent());
        }

        paymentService.updatePaymentStatus(payment, resolveStatus(session, status));
    }

    private PaymentStatus resolveStatus(Session session, PaymentStatus status) {
        if (status == PaymentStatus.SUCCEEDED && !PAYMENT_STATUS_PAID.equals(session.getPaymentStatus())) {
            return PaymentStatus.PROCESSING;
        }

        return status;
    }

    private Session extractSession(Event event) {
        StripeObject object = event.getDataObjectDeserializer().getObject().orElseGet(() -> deserializeUnsafe(event));

        if (object instanceof Session session) {
            return session;
        }

        log.warn("Stripe event {} did not carry a Checkout Session.", event.getId());

        return null;
    }

    private StripeObject deserializeUnsafe(Event event) {
        try {
            return event.getDataObjectDeserializer().deserializeUnsafe();
        } catch (EventDataObjectDeserializationException e) {
            log.warn("Could not deserialize Stripe event {}: {}", event.getId(), e.getMessage());
            return null;
        }
    }

    private Payment resolvePayment(Session session) {
        Map<String, String> metadata = session.getMetadata();
        String paymentId = metadata == null ? null : metadata.get(PaymentService.METADATA_PAYMENT_ID);

        if (paymentId == null) {
            log.warn("Stripe session {} has no {} metadata, cannot map it to a payment", session.getId(),
                    PaymentService.METADATA_PAYMENT_ID);
            return null;
        }

        return paymentCRUDService.findByPublicId(UUID.fromString(paymentId));
    }
}
