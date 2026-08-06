package camellia.ecommerce.inventory_service.kafka.events;

import java.util.UUID;
import java.util.Locale.Category;

public record ProductEvent(UUID publicId, String name, Integer numberInStock, Integer price, Category category) {
}
