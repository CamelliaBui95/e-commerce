package camellia.ecommerce.payment_service.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import camellia.ecommerce.payment_service.kafka.events.InventoryEvent;

@Configuration
public class KafkaFactoryConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        JacksonJsonSerializer<Object> serializer = new JacksonJsonSerializer<>();
        serializer.setAddTypeInfo(false);

        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, InventoryEvent> inventoryEventConsumerFactory() {
        JacksonJsonDeserializer<InventoryEvent> deserializer = new JacksonJsonDeserializer<>(InventoryEvent.class);

        deserializer.ignoreTypeHeaders();

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-service");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryEvent> inventoryEventKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, InventoryEvent>();

        factory.setConsumerFactory(inventoryEventConsumerFactory());

        return factory;
    }

}
