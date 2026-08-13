package camellia.ecommerce.order_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import camellia.ecommerce.order_service.dtos.OrderDto;
import camellia.ecommerce.order_service.entities.Order;
import camellia.ecommerce.order_service.mappers.OrderMapper;
import camellia.ecommerce.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMapper orderMapper;

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> postMethodName(@RequestBody OrderDto newOrderDto) {

        Order newOrder = orderService.createOrder(newOrderDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderMapper.toDto(newOrder));
    }

}
