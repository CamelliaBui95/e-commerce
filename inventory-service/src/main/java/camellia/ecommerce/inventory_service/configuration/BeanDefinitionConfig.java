package camellia.ecommerce.inventory_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanDefinitionConfig {

    @Bean
    public ObjectMapper initializeObjectMapper() {
        return new ObjectMapper();
    }
}
