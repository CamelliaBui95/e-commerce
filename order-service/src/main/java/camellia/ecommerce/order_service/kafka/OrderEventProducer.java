package camellia.ecommerce.order_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import camellia.ecommerce.order_service.entities.Order;
import camellia.ecommerce.order_service.kafka.events.OrderEvent;
import camellia.ecommerce.order_service.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OrderMapper orderMapper;

    public void publishOrderCreatedEvent(Order order) {

        OrderEvent orderEvent = orderMapper.toEvent(order);
        String messageKey = order.getPublicId().toString();

        kafkaTemplate.send(Topic.ORDER_CREATED.name(), messageKey, orderEvent);
        log.info("Published ORDER_CREATED event: " + orderEvent);
    }
}
