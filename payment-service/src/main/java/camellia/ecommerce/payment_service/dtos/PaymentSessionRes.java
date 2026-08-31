package camellia.ecommerce.payment_service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentSessionRes(@JsonProperty("client_secret") String clientSecret) {

}
