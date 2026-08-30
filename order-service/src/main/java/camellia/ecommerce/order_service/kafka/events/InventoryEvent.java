package camellia.ecommerce.order_service.kafka.events;

import java.util.List;
import java.util.UUID;

public record InventoryEvent(UUID orderId, List<OrderItemEvent> availableItems, List<OrderItemEvent> unavailableItems,
        Double totalPrice) {
}
