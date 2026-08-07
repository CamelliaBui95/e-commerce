package camellia.ecommerce.order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import camellia.ecommerce.order_service.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}