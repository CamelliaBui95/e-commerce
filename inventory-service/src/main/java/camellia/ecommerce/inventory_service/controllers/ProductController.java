package camellia.ecommerce.inventory_service.controllers;

import org.springframework.web.bind.annotation.RestController;

import camellia.ecommerce.inventory_service.dtos.ProductDto;
import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.mappers.ProductMapper;
import camellia.ecommerce.inventory_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    private final ProductMapper mapper;

    @GetMapping("list-products")
    public ResponseEntity<List<ProductDto>> listProducts(@RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productService.findAll(pageable);

        return ResponseEntity.ok(mapper.toDtoList(products));
    }

    @GetMapping
    public ResponseEntity<ProductDto> findProduct(@RequestParam(name = "id") UUID publicId) {
        Product foundProduct = productService.findByPublicId(publicId);

        return ResponseEntity.ok(mapper.toDto(foundProduct));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product newProduct = productService.create(productDto);

        return ResponseEntity.ok(mapper.toDto(newProduct));
    }

}
