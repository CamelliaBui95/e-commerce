package camellia.ecommerce.inventory_service.enums;

public enum ProductSortBy {
    NAME("name"), PRICE("price"), CREATED_AT("createdAt");

    private String sortBy;

    private ProductSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return this.sortBy;
    }

    public static ProductSortBy fromValue(String value) {
        for (ProductSortBy candidate : values()) {
            if (candidate.sortBy.equalsIgnoreCase(value) || candidate.name().equalsIgnoreCase(value))
                return candidate;
        }
        throw new IllegalArgumentException("Unknown sort by: " + value);
    }

    @Override
    public String toString() {
        return this.sortBy;
    }
}
