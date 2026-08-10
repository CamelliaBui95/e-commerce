package camellia.ecommerce.inventory_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import camellia.ecommerce.inventory_service.kafka.events.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    @KafkaListener(topics = "ORDER_CREATED")
    public void handleOrderCreatedEvent(OrderEvent orderEvent) {
        System.out.println("=================================");
        System.out.println("Order created event received!");
        System.out.println("order_id: " + orderEvent.publicId());
        System.out.println("=================================");
    }
}
