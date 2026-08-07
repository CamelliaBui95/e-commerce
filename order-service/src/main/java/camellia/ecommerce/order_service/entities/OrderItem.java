package camellia.ecommerce.order_service.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column(nullable = false)
    public UUID publicId;

    @Column(nullable = false)
    public UUID productId;

    @Column(nullable = false)
    public String productName;

    @Column(nullable = false)
    public Integer quantity = 0;

    @Column(nullable = false)
    public Integer unitPrice = 0;

    @ManyToOne
    public Order order;

}
