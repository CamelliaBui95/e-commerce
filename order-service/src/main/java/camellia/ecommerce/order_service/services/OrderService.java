package camellia.ecommerce.order_service.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import camellia.ecommerce.order_service.dtos.OrderDto;
import camellia.ecommerce.order_service.entities.Client;
import camellia.ecommerce.order_service.entities.Order;
import camellia.ecommerce.order_service.entities.OrderItem;
import camellia.ecommerce.order_service.enums.OrderStatus;
import camellia.ecommerce.order_service.kafka.OrderEventProducer;
import camellia.ecommerce.order_service.mappers.OrderMapper;
import camellia.ecommerce.order_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderItemService orderItemService;

    private final ClientService clientService;

    private final OrderEventProducer orderEventProducer;

    public Order create(OrderDto orderDto) {
        List<OrderItem> orderItems = orderItemService.createAll(orderDto.getItems());
        Client client = clientService.create(orderDto.getClient());

        Order order = orderMapper.toEntity(orderDto);

        order.setClient(client);
        order.setItems(orderItems);
        order.setCreatedAt(ZonedDateTime.now());
        order.setPublicId(UUID.randomUUID());
        order.setStatus(OrderStatus.INVENTORY_PENDING);
        order.setLastUpdatedAt(ZonedDateTime.now());

        Order newOrder = orderRepository.save(order);

        orderEventProducer.publishOrderCreatedEvent(newOrder);

        return newOrder;
    }

}
