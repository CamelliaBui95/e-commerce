package camellia.ecommerce.inventory_service.services;

import org.springframework.stereotype.Service;

import camellia.ecommerce.inventory_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    
}
