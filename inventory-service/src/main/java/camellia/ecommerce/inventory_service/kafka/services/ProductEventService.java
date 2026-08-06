package camellia.ecommerce.inventory_service.kafka.services;

import org.springframework.stereotype.Service;

import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.kafka.events.ProductEvent;

@Service
public class ProductEventService {

    public ProductEvent toProductEvent(Product product) {
        return new ProductEvent(product.getPublicId(), product.getName(), product.getNumberInStock(),
                product.getPrice(), product.getCategory());
    }
}
