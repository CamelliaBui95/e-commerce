package camellia.ecommerce.inventory_service.services;

import java.util.List;
import java.util.UUID;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import camellia.ecommerce.inventory_service.dtos.ProductDto;
import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.kafka.ProductEventProducer;
import camellia.ecommerce.inventory_service.kafka.ProductTopic;
import camellia.ecommerce.inventory_service.kafka.events.ProductEvent;
import camellia.ecommerce.inventory_service.kafka.services.ProductEventService;
import camellia.ecommerce.inventory_service.mappers.ProductMapper;
import camellia.ecommerce.inventory_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductEventProducer productEventProducer;

    private final ProductEventService productEventService;

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    public Product create(ProductDto productDto) {
        Product newProduct = mapper.toEntity(productDto);
        newProduct.setPublicId(UUID.randomUUID());

        Product savedProduct = productRepository.save(newProduct);

        ProductEvent productEvent = productEventService.toProductEvent(savedProduct);
        productEventProducer.publishProductEvent(productEvent, ProductTopic.PRODUCT_CREATED);

        return savedProduct;
    }

    public ProductDto findByPublicId(UUID publicId) {
        Product product = productRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        return mapper.toDto(product);
    }

    public List<ProductDto> findAll(Pageable page) {
        List<Product> products = productRepository.findAll(page).getContent();

        return mapper.toDtoList(products);
    }
}
