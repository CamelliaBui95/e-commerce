package camellia.ecommerce.inventory_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import camellia.ecommerce.inventory_service.entities.Product;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByPublicId(UUID publicId);
}
