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
import camellia.ecommerce.order_service.kafka.events.PaymentEvent;
import camellia.ecommerce.order_service.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OrderCRUDService orderCRUDService;

    private final OrderSSEService orderSSEService;

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

        OrderStatus status = OrderStatus.INVENTORY_REJECTED;
        order.setStatus(status);

        List<UUID> unavailableItems = inventoryEvent.unavailableItems().stream().map(item -> item.itemId()).toList();

        order.getItems().stream().forEach(item -> {
            if (unavailableItems.contains(item.getPublicId())) {
                item.setStatus(OrderItemStatus.UNAVAILABLE);
            } else {
                item.setStatus(OrderItemStatus.AVAILABLE);
            }
        });

        orderCRUDService.save(order);
        orderSSEService.sendStatus(orderPublicId, status);
    }

    @KafkaListener(topics = "INVENTORY_RESERVED", containerFactory = "inventoryEventKafkaListenerContainerFactory")
    public void handleInventoryReservedEvent(InventoryEvent inventoryEvent) {
        UUID orderId = inventoryEvent.orderId();

        Order order = findOrder(orderId);

        OrderStatus status = OrderStatus.INVENTORY_RESERVED;
        order.setStatus(status);

        order.getItems().stream().forEach(item -> {
            item.setStatus(OrderItemStatus.AVAILABLE);
        });

        orderCRUDService.save(order);
        orderSSEService.sendStatus(orderId, status);
    }

    @KafkaListener(topics = "PAYMENT_PENDING", containerFactory = "paymentEventKafkaListenerContainerFactory")
    public void handlePaymentPendingEvent(PaymentEvent event) {
        handlePaymentEvents(event, OrderStatus.PAYMENT_PENDING);
    }

    @KafkaListener(topics = "PAYMENT_SUCCEEDED", containerFactory = "paymentEventKafkaListenerContainerFactory")
    public void handlePaymentSucceeded(PaymentEvent event) {
        handlePaymentEvents(event, OrderStatus.PAYMENT_SUCCEEDED);
    }

    public void publishOrderCreatedEvent(Order order) {

        OrderEvent orderEvent = orderMapper.toEvent(order);
        String messageKey = order.getPublicId().toString();

        kafkaTemplate.send(Topic.ORDER_CREATED.name(), messageKey, orderEvent);
        orderSSEService.sendStatus(order.getPublicId(), order.getStatus());

        log.info("Published ORDER_CREATED event: " + orderEvent);
    }

    public Order findOrder(UUID publicId) {
        return orderCRUDService.findByPublicId(publicId);
    }

    private void handlePaymentEvents(PaymentEvent event, OrderStatus status) {
        UUID orderId = event.orderId();

        Order order = findOrder(orderId);

        order.setStatus(status);

        orderCRUDService.save(order);
        orderSSEService.sendStatus(orderId, status);
    }

}
