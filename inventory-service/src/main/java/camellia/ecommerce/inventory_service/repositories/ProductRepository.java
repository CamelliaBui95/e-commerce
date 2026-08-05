package camellia.ecommerce.inventory_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import camellia.ecommerce.inventory_service.entities.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByPublicId(UUID publicId);
}
