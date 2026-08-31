package camellia.ecommerce.order_service.kafka.events;

import java.util.UUID;

public record PaymentEvent(UUID orderId, UUID paymentId) {

}
