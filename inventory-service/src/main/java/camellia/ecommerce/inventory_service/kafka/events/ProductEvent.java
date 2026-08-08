package camellia.ecommerce.inventory_service.kafka.events;

import java.util.UUID;

import camellia.ecommerce.inventory_service.enums.Category;

public record ProductEvent(UUID publicId, String name, Integer numberInStock, Double price, Category category) {

}
