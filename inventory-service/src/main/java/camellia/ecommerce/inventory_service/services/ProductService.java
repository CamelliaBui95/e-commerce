package camellia.ecommerce.inventory_service.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import camellia.ecommerce.inventory_service.dtos.ProductDto;
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

        return productRepository.save(newProduct);
    }
}
