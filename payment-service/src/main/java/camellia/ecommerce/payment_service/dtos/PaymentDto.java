package camellia.ecommerce.payment_service.dtos;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import camellia.ecommerce.payment_service.enums.PaymentStatus;

public record PaymentDto(@JsonProperty("id") UUID publicId, @JsonProperty("order_id") UUID orderId,
        @JsonProperty("amount") BigDecimal amount, PaymentStatus status, String stripePaymentSessionId,
        @JsonProperty("created_at") ZonedDateTime createdAt, @JsonProperty("processed_at") ZonedDateTime processedAt) {
}
