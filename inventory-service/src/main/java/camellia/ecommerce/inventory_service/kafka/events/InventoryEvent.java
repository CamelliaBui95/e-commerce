package camellia.ecommerce.inventory_service.kafka.events;

import java.util.List;

public record InventoryEvent(List<OrderItemEvent> availableItems, List<OrderItemEvent> unavailableItems) {
}
