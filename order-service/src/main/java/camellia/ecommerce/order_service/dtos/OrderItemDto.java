package camellia.ecommerce.order_service.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public record OrderItemDto(@JsonProperty("id") @JsonInclude(Include.NON_EMPTY) UUID publicId,
        @JsonProperty("product_id") UUID productId, Integer quantity, @JsonProperty("unit_price") Double unitPrice) {
}
