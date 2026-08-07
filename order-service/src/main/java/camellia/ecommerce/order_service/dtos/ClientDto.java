package camellia.ecommerce.order_service.dtos;

import java.util.UUID;

public record ClientDto(
    UUID publicId,
    String firstName,
    String lastName,
    String address,
    String email,
    String phoneNumber
) {
    
}
