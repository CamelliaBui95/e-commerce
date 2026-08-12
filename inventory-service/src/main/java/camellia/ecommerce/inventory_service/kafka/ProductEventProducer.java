package camellia.ecommerce.inventory_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import camellia.ecommerce.inventory_service.kafka.events.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEventProducer {

    // private final KafkaTemplate<String, String> kafkaTemplate;

    // private final ObjectMapper mapper;

    // public void publishProductEvent(ProductEvent event, Topic topic) {
    // String message;
    // try {
    // message = mapper.writeValueAsString(event);
    // kafkaTemplate.send(topic.name(), event.publicId().toString(), message);
    // log.info("Published product event: " + message);
    // } catch (JsonProcessingException e) {
    // throw new IllegalStateException("Could not serialize ProductEvent", e);
    // }

    // }
}
