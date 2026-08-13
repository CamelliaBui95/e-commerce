package camellia.ecommerce.order_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import camellia.ecommerce.order_service.dtos.OrderItemDto;
import camellia.ecommerce.order_service.entities.OrderItem;
import camellia.ecommerce.order_service.enums.OrderItemStatus;
import camellia.ecommerce.order_service.mappers.OrderItemMapper;
import camellia.ecommerce.order_service.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final OrderItemMapper orderItemMapper;

    public List<OrderItem> createAll(List<OrderItemDto> orderItemDto) {

        List<OrderItem> newItems = orderItemDto.stream().map(dto -> {
            OrderItem newItem = orderItemMapper.toEntity(dto);
            newItem.setPublicId(UUID.randomUUID());
            newItem.setStatus(OrderItemStatus.REQUESTED);
            return newItem;
        }).toList();

        return orderItemRepository.saveAll(newItems);
    }

}
