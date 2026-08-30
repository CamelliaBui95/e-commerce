package camellia.ecommerce.inventory_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import camellia.ecommerce.inventory_service.entities.InventoryReservation;

public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, Long>, JpaSpecificationExecutor<InventoryReservation> {

    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM InventoryReservation i WHERE i.productId = :productId AND i.status = camellia.ecommerce.inventory_service.enums.ReservationStatus.RESERVED")
    int findReservedQuantityOfProduct(UUID productId);

}