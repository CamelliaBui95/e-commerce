package camellia.ecommerce.order_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import camellia.ecommerce.order_service.kafka.Topic;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderCreatedTopic() {
        return createTopic(Topic.ORDER_CREATED);
    }

    @Bean
    public NewTopic orderCancelledTopic() {
        return createTopic(Topic.ORDER_CANCELLED);
    }

    private NewTopic createTopic(Topic topic) {
        return TopicBuilder.name(topic.name()).partitions(1).replicas(1).build();
    }

}
