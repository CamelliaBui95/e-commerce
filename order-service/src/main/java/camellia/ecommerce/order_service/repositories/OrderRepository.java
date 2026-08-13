package camellia.ecommerce.order_service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import camellia.ecommerce.order_service.entities.Order;
import camellia.ecommerce.order_service.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "items")
    Optional<Order> findByPublicId(UUID publicId);

    @Modifying
    @Query("UPDATE Order o set o.status = :orderStatus where o.id = :id")
    void updateOrderStatus(Long id, OrderStatus orderStatus);
}
