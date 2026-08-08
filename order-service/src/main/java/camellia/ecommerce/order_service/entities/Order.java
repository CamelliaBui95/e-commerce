package camellia.ecommerce.order_service.entities;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import camellia.ecommerce.order_service.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private UUID publicId;

    @OneToMany
    private List<OrderItem> items;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    private ZonedDateTime lastUpdatedAt;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne
    private Client client;
}
