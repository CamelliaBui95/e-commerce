package camellia.ecommerce.inventory_service.kafka.events;

import java.util.UUID;

public record OrderItemEvent(UUID itemId, UUID productId, Integer quantity) {
}
