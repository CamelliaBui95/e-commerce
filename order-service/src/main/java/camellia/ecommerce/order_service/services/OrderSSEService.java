package camellia.ecommerce.order_service.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import camellia.ecommerce.order_service.dtos.OrderStatusEvent;
import camellia.ecommerce.order_service.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderSSEService {
    private final Map<UUID, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(UUID orderId) {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);
        emitters.computeIfAbsent(orderId, id -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> remove(orderId, emitter));
        emitter.onTimeout(() -> remove(orderId, emitter));
        emitter.onError(error -> {
            log.error(error.toString());
            remove(orderId, emitter);
        });

        return emitter;
    }

    public void sendStatus(UUID orderId, OrderStatus status) {
        List<SseEmitter> orderEmitters = emitters.get(orderId);

        if (orderEmitters == null) {
            return;
        }

        OrderStatusEvent event = new OrderStatusEvent(orderId, status);

        for (SseEmitter emitter : orderEmitters) {
            try {
                emitter.send(SseEmitter.event().name("order-status").data(event));
            } catch (Exception e) {
                remove(orderId, emitter);
            }
        }
    }

    private void remove(UUID orderId, SseEmitter emitter) {
        List<SseEmitter> orderEmitters = emitters.get(orderId);

        if (orderEmitters == null) {
            return;
        }

        orderEmitters.remove(emitter);

        if (orderEmitters.isEmpty()) {
            emitters.remove(orderId);
        }
    }

}
