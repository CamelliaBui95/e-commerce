package camellia.ecommerce.payment_service.services;

import java.math.BigDecimal;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import camellia.ecommerce.payment_service.kafka.events.InventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final PaymentCRUDService paymentCRUDService;

    @KafkaListener(topics = "INVENTORY_RESERVED", containerFactory = "inventoryEventKafkaListenerContainerFactory")
    public void handleInventoryReservedEvent(InventoryEvent event) {
        paymentCRUDService.create(event.orderId(), new BigDecimal(event.totalPrice()));
    }
}
