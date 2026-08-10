package camellia.ecommerce.order_service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import camellia.ecommerce.order_service.dtos.OrderItemDto;
import camellia.ecommerce.order_service.entities.OrderItem;
import camellia.ecommerce.order_service.kafka.events.OrderItemEvent;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toDTO(OrderItem item);

    @Mapping(target = "id", ignore = true)
    OrderItem toEntity(OrderItemDto itemDto);

    List<OrderItemDto> toDtoList(List<OrderItem> items);

    @Mapping(target = "itemId", source = "publicId")
    OrderItemEvent toEvent(OrderItem item);

    List<OrderItemEvent> toEventList(List<OrderItem> items);
}
