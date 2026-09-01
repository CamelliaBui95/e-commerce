package camellia.ecommerce.inventory_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import camellia.ecommerce.inventory_service.entities.InventoryReservation;
import camellia.ecommerce.inventory_service.enums.ReservationStatus;

import java.util.List;

public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, Long>, JpaSpecificationExecutor<InventoryReservation> {

    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM InventoryReservation i WHERE i.productId = :productId AND i.status = camellia.ecommerce.inventory_service.enums.ReservationStatus.RESERVED")
    int findReservedQuantityOfProduct(UUID productId);

    List<InventoryReservation> findByOrderId(UUID orderId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE InventoryReservation ir SET ir.status = :status WHERE ir.id IN :ids")
    int updateReservationStatus(@Param("ids") List<Long> ids, @Param("status") ReservationStatus status);

}