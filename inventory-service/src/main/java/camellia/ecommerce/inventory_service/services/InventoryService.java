package camellia.ecommerce.inventory_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.enums.ReservationStatus;
import camellia.ecommerce.inventory_service.kafka.Topic;
import camellia.ecommerce.inventory_service.kafka.events.InventoryEvent;
import camellia.ecommerce.inventory_service.kafka.events.OrderEvent;
import camellia.ecommerce.inventory_service.kafka.events.OrderItemEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductService productService;

    private final InventoryReservationService inventoryReservationService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "ORDER_CREATED", containerFactory = "orderEventKafkaListenerContainerFactory")
    public void handleOrderCreatedEvent(OrderEvent orderEvent) {
        List<OrderItemEvent> items = orderEvent.items();

        List<OrderItemEvent> availableItems = new ArrayList<>();
        List<OrderItemEvent> unavailableItems = new ArrayList<>();

        items.stream().forEach(item -> {
            Product product = productService.findByPublicId(item.productId());
            long numberReserved = inventoryReservationService.findReservedQuantityOfProduct(item.productId());
            long availableStock = product.getNumberInStock() - numberReserved;

            if (item.quantity() <= availableStock) {
                availableItems.add(item);
            } else {
                unavailableItems.add(item);
            }
        });

        if (!unavailableItems.isEmpty()) {
            publishInventoryRejectedEvent(orderEvent.publicId(), availableItems, unavailableItems);
        } else {

            availableItems.stream().forEach(item -> {
                inventoryReservationService.create(orderEvent.publicId(), item.productId(), item.quantity(),
                        ReservationStatus.RESERVED);
            });

            publishInventoryReservedEvent(orderEvent.publicId(), availableItems, unavailableItems);
        }

    }

    public void publishInventoryReservedEvent(UUID orderId, List<OrderItemEvent> availableItems,
            List<OrderItemEvent> unavailableItems) {

        Double totalPrice = availableItems.stream().map(item -> item.unitPrice() * item.quantity()).reduce(0.0,
                (subtotal, number) -> subtotal + number);

        InventoryEvent inventoryEvent = new InventoryEvent(orderId, availableItems, unavailableItems, totalPrice);
        publishInventoryEvent(orderId, inventoryEvent, Topic.INVENTORY_RESERVED);
    }

    public void publishInventoryRejectedEvent(UUID orderId, List<OrderItemEvent> availableItems,
            List<OrderItemEvent> unavailableItems) {
        InventoryEvent inventoryEvent = new InventoryEvent(orderId, availableItems, unavailableItems, 0.0);
        publishInventoryEvent(orderId, inventoryEvent, Topic.INVENTORY_REJECTED);
    }

    private void publishInventoryEvent(UUID key, InventoryEvent inventoryEvent, Topic topic) {
        kafkaTemplate.send(topic.name(), key.toString(), inventoryEvent);
        log.info(topic.name());
    }

}

// stock: 10
// reserved: 5
// available: 10 - 5 = 5
// quantity: 2