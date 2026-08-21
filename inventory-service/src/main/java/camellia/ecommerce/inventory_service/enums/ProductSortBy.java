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

    @Override
    public String toString() {
        return this.sortBy;
    }
}
