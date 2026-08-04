package camellia.ecommerce.inventory_service.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import camellia.ecommerce.inventory_service.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {

    @JsonProperty("public_id")
    @JsonInclude(Include.NON_EMPTY)
    private UUID publicId;

    private String name;

    @JsonProperty("stock")
    private Integer numberInStock;

    private Integer price;

    private Category category;

    @JsonProperty("image_path")
    @JsonInclude(Include.NON_EMPTY)
    private String imagePath;

}
