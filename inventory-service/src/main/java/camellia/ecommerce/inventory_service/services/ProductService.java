package camellia.ecommerce.inventory_service.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camellia.ecommerce.inventory_service.dtos.ProductDto;
import camellia.ecommerce.inventory_service.dtos.ProductSearchQuery;
import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.mappers.ProductMapper;
import camellia.ecommerce.inventory_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    public Product create(ProductDto productDto) {
        Product newProduct = mapper.toEntity(productDto);
        newProduct.setPublicId(UUID.randomUUID());
        newProduct.setCreatedAt(ZonedDateTime.now());

        Product savedProduct = save(newProduct);

        return savedProduct;
    }

    public Page<Product> search(ProductSearchQuery query) {
        Specification<Product> spec = ProductSpecifications.fromQuery(query);
        Pageable pageable = ProductSpecifications.createPageable(query);
        return productRepository.findAll(spec, pageable);
    }

    public Product findByPublicId(UUID publicId) {
        Product product = productRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        return product;
    }

    public List<Product> findAll(Pageable page) {
        return productRepository.findAll(page).getContent();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void updateStocks(Map<UUID, Long> numberReservedByProductId) {
        List<Product> products = productRepository.findAllByPublicIdIn(numberReservedByProductId.keySet());

        for (Product product : products) {
            UUID productId = product.getPublicId();
            Long newStock = product.getNumberInStock() - numberReservedByProductId.get(productId);
            product.setNumberInStock(newStock);
        }
    }

}
