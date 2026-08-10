package camellia.ecommerce.order_service.kafka;

import org.springframework.stereotype.Service;

@Service
public class ProductEventListener {

    // @KafkaListener(topics = "PRODUCT_CREATED")
    // public void listen(ConsumerRecord<String, String> message) {
    //     System.out.println("=================================");
    //     System.out.println("Product created event received!");
    //     System.out.println("Key: " + message.key());
    //     System.out.println("Value: " + message.value());
    //     System.out.println("Topic: " + message.topic());
    //     System.out.println("Partition: " + message.partition());
    //     System.out.println("Offset: " + message.offset());
    //     System.out.println("=================================");
    // }
}
