package camellia.ecommerce.inventory_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import camellia.ecommerce.inventory_service.dtos.ProductSearchQuery;
import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.enums.Category;
import camellia.ecommerce.inventory_service.enums.ProductSortBy;
import camellia.ecommerce.inventory_service.enums.SortDirection;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ProductSpecifications {

    public static Specification<Product> fromQuery(ProductSearchQuery query) {

        List<Specification<Product>> specs = new ArrayList<>();

        String productName = query.getName();
        if (productName != null && !productName.isBlank()) {
            specs.add(containsName(productName));
        }

        Category category = query.getCategory();
        if (category != null) {
            specs.add(hasCategory(category));
        }

        return specs.isEmpty() ? emptySpec()
                : specs.stream().filter(Objects::nonNull).reduce(Specification::and).orElseThrow();
    }

    private static Specification<Product> emptySpec() {
        return (root, query, cb) -> cb.conjunction();
    }

    private static Specification<Product> hasCategory(Category category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    private static Specification<Product> containsName(String name) {
        String searchedName = String.format("%", name.toLowerCase(), "%");
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), searchedName);
    }

    public static Pageable createPageable(ProductSearchQuery query) {

        int pageNumber = query.getPageNumber() != null ? query.getPageNumber() : 0;
        int pageSize = query.getPageSize() != null ? query.getPageSize() : 10;
        SortDirection direction = query.getDirection() != null ? query.getDirection() : SortDirection.DESC;

        ProductSortBy sortBy = query.getSortBy() != null ? query.getSortBy() : ProductSortBy.CREATED_AT;

        Sort sort = direction == SortDirection.ASC ? Sort.by(sortBy.toString()).ascending()
                : Sort.by(sortBy.toString()).descending();

        return PageRequest.of(pageNumber, pageSize, sort);
    }

}
