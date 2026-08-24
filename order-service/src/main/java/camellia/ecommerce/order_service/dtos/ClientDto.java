package camellia.ecommerce.order_service.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientDto(@JsonProperty("id") UUID publicId, @JsonProperty("first_name") String firstName,
                @JsonProperty("last_name") String lastName, @JsonProperty("address") String address, String email,
                @JsonProperty("phone_number") String phoneNumber) {
}
