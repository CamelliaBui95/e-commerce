package camellia.ecommerce.order_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import camellia.ecommerce.order_service.dtos.OrderDto;
import camellia.ecommerce.order_service.entities.Order;
import camellia.ecommerce.order_service.mappers.OrderMapper;
import camellia.ecommerce.order_service.services.OrderSSEService;
import camellia.ecommerce.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMapper orderMapper;

    private final OrderService orderService;

    private final OrderSSEService orderSSEService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto newOrderDto) {

        Order newOrder = orderService.createOrder(newOrderDto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderMapper.toDto(newOrder));
    }

    @GetMapping(value = "/{orderId}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@PathVariable UUID orderId) {
        Order order = orderService.findOrder(orderId);
        
        SseEmitter emitter = orderSSEService.subscribe(orderId);

        orderSSEService.sendStatus(orderId, order.getStatus());
        return ResponseEntity.ok(emitter);

    }

}
