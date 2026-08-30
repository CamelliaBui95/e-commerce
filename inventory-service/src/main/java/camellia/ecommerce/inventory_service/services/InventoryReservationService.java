package camellia.ecommerce.inventory_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import camellia.ecommerce.inventory_service.entities.InventoryReservation;
import camellia.ecommerce.inventory_service.enums.ReservationStatus;
import camellia.ecommerce.inventory_service.repositories.InventoryReservationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryReservationService {

    private final InventoryReservationRepository repository;

    public InventoryReservation create(UUID orderId, UUID productId, Long quantity, ReservationStatus status) {
        InventoryReservation newReservation = new InventoryReservation(orderId, productId, quantity, status);

        return repository.save(newReservation);
    }

    public long findReservedQuantityOfProduct(UUID productId) {
        return repository.findReservedQuantityOfProduct(productId);
    }

}
