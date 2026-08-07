package camellia.ecommerce.order_service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import camellia.ecommerce.order_service.dtos.OrderDto;
import camellia.ecommerce.order_service.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "client", ignore = true)
    Order toEntity(OrderDto orderDto);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "client", ignore = true)
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);
}
