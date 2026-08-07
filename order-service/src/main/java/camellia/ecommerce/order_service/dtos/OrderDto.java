package camellia.ecommerce.order_service.dtos;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    @JsonProperty("id")
    @JsonInclude(Include.NON_EMPTY)
    private UUID publicId;

    private List<OrderItemDto> items;

    @JsonProperty("created_at")
    @JsonInclude(Include.NON_EMPTY)
    private ZonedDateTime createdAt;

    private ClientDto client;

}
