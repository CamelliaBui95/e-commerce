package camellia.ecommerce.order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import camellia.ecommerce.order_service.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
