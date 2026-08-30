package camellia.ecommerce.order_service.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import camellia.ecommerce.order_service.dtos.OrderDto;
import camellia.ecommerce.order_service.entities.Client;
import camellia.ecommerce.order_service.entities.Order;
import camellia.ecommerce.order_service.entities.OrderItem;
import camellia.ecommerce.order_service.enums.OrderStatus;
import camellia.ecommerce.order_service.mappers.OrderMapper;
import camellia.ecommerce.order_service.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCRUDService {
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderItemService orderItemService;

    private final ClientService clientService;

    @Transactional
    public Order create(OrderDto orderDto) {
        List<OrderItem> orderItems = orderItemService.createAll(orderDto.getItems());
        Client client = clientService.create(orderDto.getClient());

        Order order = orderMapper.toEntity(orderDto);

        order.setClient(client);
        order.setItems(orderItems);
        order.setCreatedAt(ZonedDateTime.now());
        order.setPublicId(UUID.randomUUID());
        order.setStatus(OrderStatus.ORDER_CREATING);
        order.setLastUpdatedAt(ZonedDateTime.now());

        return orderRepository.save(order);
    }

    public Order findByPublicId(UUID publicId) {
        return orderRepository.findByPublicId(publicId).orElseThrow(() -> new ResourceNotFoundException(null));
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    // public void updateOrderStatus(Order order, OrderStatus orderStatus) {
    // orderRepository.updateOrderStatus(order.getId(), orderStatus);
    // }

}
