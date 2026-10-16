package camellia.ecommerce.payment_service.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import camellia.ecommerce.payment_service.enums.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Valid
public record MockPaymentReq(@NotNull @JsonProperty("payment_id") UUID paymentId, @NotNull PaymentStatus status) {
}
