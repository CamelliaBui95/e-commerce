package camellia.ecommerce.order_service.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import camellia.ecommerce.order_service.enums.OrderStatus;

public record OrderStatusEvent(@JsonProperty("order_id") UUID orderId, OrderStatus status) {

}
