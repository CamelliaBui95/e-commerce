package camellia.ecommerce.order_service.enums;

public enum OrderStatus {
    INVENTORY_PENDING, INVENTORY_RESERVED, INVENTORY_REJECTED,
    PAYMENT_PENDING, PAYMENT_SUCCEEDED, PAYMENT_FAILED,
    CANCELLED
}
