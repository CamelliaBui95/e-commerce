package camellia.ecommerce.payment_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import camellia.ecommerce.payment_service.kafka.Topic;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic paymentPendingTopic() {
        return createTopic(Topic.PAYMENT_PENDING);
    }

    @Bean
    public NewTopic paymentSucceededTopic() {
        return createTopic(Topic.PAYMENT_SUCCEEDED);
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return createTopic(Topic.PAYMENT_FAILED);
    }

    private NewTopic createTopic(Topic topic) {
        return TopicBuilder.name(topic.name()).partitions(1).replicas(1).build();
    }

}
