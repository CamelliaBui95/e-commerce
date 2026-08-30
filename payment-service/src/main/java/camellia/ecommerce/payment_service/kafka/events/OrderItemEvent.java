package camellia.ecommerce.payment_service.kafka.events;

import java.util.UUID;

public record OrderItemEvent(UUID itemId, UUID productId, Long quantity, Double unitPrice) {
}
