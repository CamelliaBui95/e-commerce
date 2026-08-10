package camellia.ecommerce.inventory_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import camellia.ecommerce.inventory_service.kafka.Topic;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic productCreatedTopic() {
        return TopicBuilder.name(Topic.PRODUCT_CREATED.name()).partitions(1).replicas(1).build();
    }
}
