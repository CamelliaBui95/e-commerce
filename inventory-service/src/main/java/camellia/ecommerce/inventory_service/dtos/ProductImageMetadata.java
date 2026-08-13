package camellia.ecommerce.inventory_service.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductImageMetadata(@JsonProperty("product_id") UUID productId,
        @JsonProperty("image_name") String imageName) {

}
