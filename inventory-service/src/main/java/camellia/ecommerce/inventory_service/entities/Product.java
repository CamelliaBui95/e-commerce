package camellia.ecommerce.inventory_service.entities;

import java.time.ZonedDateTime;
import java.util.UUID;

import camellia.ecommerce.inventory_service.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private Integer numberReserved = 0;

    @Column(nullable = false)
    private Double price = 0.0;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = true)
    private String imagePath;

    @Column(nullable = false)
    private ZonedDateTime createdAt;
}
