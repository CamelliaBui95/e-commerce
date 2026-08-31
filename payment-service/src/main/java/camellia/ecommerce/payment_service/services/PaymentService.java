package camellia.ecommerce.payment_service.services;

import java.math.BigDecimal;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import camellia.ecommerce.payment_service.entities.Payment;
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

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final PaymentCRUDService paymentCRUDService;

    private final PaymentMapper paymentMapper;

    @KafkaListener(topics = "INVENTORY_RESERVED", containerFactory = "inventoryEventKafkaListenerContainerFactory")
    public void handleInventoryReservedEvent(InventoryEvent event) {
        Payment payment = paymentCRUDService.create(event.orderId(), new BigDecimal(event.totalPrice()));
        publishPaymentPendingEvent(payment);
    }

    public void publishPaymentPendingEvent(Payment payment) {
        PaymentEvent event = paymentMapper.toEvent(payment);

        kafkaTemplate.send(Topic.PAYMENT_PENDING.name(), event.paymentId().toString(), event);
        log.info("Published PAYMENT_PENDING event: " + event);
    }
}
