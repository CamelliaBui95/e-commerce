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

        String productName = query.getName().orElse(null);
        if (productName != null && !productName.isBlank()) {
            specs.add(containsName(productName));
        }

        Category category = query.getCategory().orElse(null);
        if (category != null) {
            specs.add(hasCategory(category));
        }

        return specs.stream().filter(Objects::nonNull).reduce(Specification::and).orElseThrow();
    }

    private static Specification<Product> hasCategory(Category category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    private static Specification<Product> containsName(String name) {
        String searchedName = String.format("%", name.toLowerCase(), "%");
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), searchedName);
    }

    public static Pageable createPageable(ProductSearchQuery query) {

        int pageNumber = query.getPageNumber().isPresent() ? query.getPageNumber().get() : 0;
        int pageSize = query.getPageSize().isPresent() ? query.getPageSize().get() : 10;
        SortDirection direction = query.getDirection().isPresent() ? query.getDirection().get() : SortDirection.DESC;

        ProductSortBy sortBy = query.getSortBy().isPresent() ? query.getSortBy().get() : ProductSortBy.CREATED_AT;

        Sort sort = direction == SortDirection.ASC ? Sort.by(sortBy.toString()).ascending()
                : Sort.by(sortBy.toString()).descending();

        return PageRequest.of(pageNumber, pageSize, sort);
    }

}
