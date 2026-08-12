package camellia.ecommerce.inventory_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import camellia.ecommerce.inventory_service.entities.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByPublicId(UUID publicId);

    @Modifying
    @Query("UPDATE Product p set p.numberReserved = :numberReserved where p.id = :id")
    void updateNumberReserved(Long id, int numberReserved);
}
