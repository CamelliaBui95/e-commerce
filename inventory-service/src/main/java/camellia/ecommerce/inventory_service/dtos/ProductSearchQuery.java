package camellia.ecommerce.inventory_service.dtos;

import camellia.ecommerce.inventory_service.enums.Category;
import camellia.ecommerce.inventory_service.enums.ProductSortBy;
import camellia.ecommerce.inventory_service.enums.SortDirection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class ProductSearchQuery {

    private Integer pageNumber;

    @Min(1)
    @Max(100)
    private Integer pageSize;

    private String name;

    private ProductSortBy sortBy;

    private SortDirection direction;

    private Category category;
}
