package camellia.ecommerce.inventory_service.dtos;

import java.time.ZonedDateTime;
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

    @JsonProperty("id")
    @JsonInclude(Include.NON_EMPTY)
    private UUID publicId;

    private String name;

    @JsonProperty("stock")
    private Integer numberInStock;

    @JsonProperty("number_reserved")
    @JsonInclude(Include.NON_EMPTY)
    private Integer numberReserved;

    private Double price;

    private Category category;

    @JsonProperty("image_name")
    @JsonInclude(Include.NON_EMPTY)
    private String imageName;

    @JsonProperty("created_at")
    @JsonInclude(Include.NON_EMPTY)
    private ZonedDateTime createdAt;

}
