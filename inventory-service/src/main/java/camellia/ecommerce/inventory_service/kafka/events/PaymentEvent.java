package camellia.ecommerce.inventory_service.kafka.events;

import java.util.UUID;

public record PaymentEvent(UUID orderId, UUID paymentId) {

}
