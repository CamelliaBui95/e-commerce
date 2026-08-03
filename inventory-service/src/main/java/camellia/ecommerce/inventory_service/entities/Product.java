package camellia.ecommerce.inventory_service.entities;

import java.util.UUID;

import camellia.ecommerce.inventory_service.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    private Long id;

    @Column(nullable = false)
    private UUID publicId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private long numberInStock = 0;

    @Column(nullable = false)
    @Builder.Default
    private int price = 0;
    
    @Column(nullable = false)
    private Category category;

    @Column(nullable = true)
    private String imagePath;
}
