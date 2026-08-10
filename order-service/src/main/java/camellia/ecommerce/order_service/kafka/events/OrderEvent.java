package camellia.ecommerce.order_service.kafka.events;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record OrderEvent(UUID publicId, ZonedDateTime createdAt, List<OrderItemEvent> items) {
}
