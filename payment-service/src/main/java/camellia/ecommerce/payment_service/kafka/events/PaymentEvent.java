package camellia.ecommerce.payment_service.kafka.events;

import java.util.UUID;

import camellia.ecommerce.payment_service.enums.PaymentStatus;

public record PaymentEvent(UUID orderId, UUID paymentId, PaymentStatus status) {

}
