package camellia.ecommerce.inventory_service.controllers;

import org.springframework.web.bind.annotation.RestController;

import camellia.ecommerce.inventory_service.dtos.ProductDto;
import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.mappers.ProductMapper;
import camellia.ecommerce.inventory_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    private final ProductMapper mapper;

    @PostMapping
    public ResponseEntity<ProductDto> postMethodName(@RequestBody ProductDto productDto) {
        Product newProduct = productService.create(productDto);

        return ResponseEntity.ok(mapper.toDto(newProduct));
    }

}
