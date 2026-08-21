package camellia.ecommerce.inventory_service.dtos;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import camellia.ecommerce.inventory_service.enums.Category;
import camellia.ecommerce.inventory_service.enums.ProductSortBy;
import camellia.ecommerce.inventory_service.enums.SortDirection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchQuery {

    @JsonProperty("page_number")
    private Optional<Integer> pageNumber;

    @Min(1)
    @Max(100)
    @JsonProperty("page_size")
    private Optional<Integer> pageSize;

    private Optional<String> name;

    @JsonProperty("sort_by")
    private Optional<ProductSortBy> sortBy;

    private Optional<SortDirection> direction;

    private Optional<Category> category;
}
