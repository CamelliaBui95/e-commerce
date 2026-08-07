package camellia.ecommerce.order_service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import camellia.ecommerce.order_service.dtos.OrderItemDto;
import camellia.ecommerce.order_service.entities.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    
    OrderItemDto toDTO(OrderItem item);

    @Mapping(target = "id", ignore = true)
    OrderItem toEntity(OrderItemDto itemDto);

    List<OrderItemDto> toDtoList(List<OrderItem> items);
}
