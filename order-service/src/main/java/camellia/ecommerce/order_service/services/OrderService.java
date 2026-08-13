package camellia.ecommerce.order_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import camellia.ecommerce.order_service.dtos.OrderDto;
import camellia.ecommerce.order_service.entities.Order;
import camellia.ecommerce.order_service.enums.OrderItemStatus;
import camellia.ecommerce.order_service.enums.OrderStatus;
import camellia.ecommerce.order_service.kafka.Topic;
import camellia.ecommerce.order_service.kafka.events.InventoryEvent;
import camellia.ecommerce.order_service.kafka.events.OrderEvent;
import camellia.ecommerce.order_service.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OrderCRUDService orderCRUDService;

    private final OrderMapper orderMapper;

    public Order createOrder(OrderDto newOrderDto) {

        Order newOrder = orderCRUDService.create(newOrderDto);
        publishOrderCreatedEvent(newOrder);

        return newOrder;
    }

    @KafkaListener(topics = "INVENTORY_REJECTED", containerFactory = "inventoryEventKafkaListenerContainerFactory")
    public void handleInventoryRejectedEvent(InventoryEvent inventoryEvent) {
        UUID orderPublicId = inventoryEvent.orderId();

        Order order = orderCRUDService.findByPublicId(orderPublicId);
        order.setStatus(OrderStatus.INVENTORY_REJECTED);

        List<UUID> unavailableItems = inventoryEvent.unavailableItems().stream().map(item -> item.itemId()).toList();

        order.getItems().stream().forEach(item -> {
            if (unavailableItems.contains(item.getPublicId())) {
                item.setStatus(OrderItemStatus.UNAVAILABLE);
            } else {
                item.setStatus(OrderItemStatus.AVAILABLE);
            }
        });

        orderCRUDService.save(order);
    }

    @KafkaListener(topics = "INVENTORY_RESERVED", containerFactory = "inventoryEventKafkaListenerContainerFactory")
    public void handleInventoryReservedEvent(InventoryEvent inventoryEvent) {

    }

    public void publishOrderCreatedEvent(Order order) {

        OrderEvent orderEvent = orderMapper.toEvent(order);
        String messageKey = order.getPublicId().toString();

        kafkaTemplate.send(Topic.ORDER_CREATED.name(), messageKey, orderEvent);
        log.info("Published ORDER_CREATED event: " + orderEvent);
    }

}
