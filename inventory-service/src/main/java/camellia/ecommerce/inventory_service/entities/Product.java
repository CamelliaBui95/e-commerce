package camellia.ecommerce.inventory_service.entities;

import java.util.UUID;

import camellia.ecommerce.inventory_service.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private UUID publicId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer numberInStock = 0;

    @Column(nullable = false)
    private Integer numberReserved = 0;

    @Column(nullable = false)
    private Integer price = 0;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = true)
    private String imagePath;
}
