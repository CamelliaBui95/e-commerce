package camellia.ecommerce.inventory_service.controllers;

import org.springframework.web.bind.annotation.RestController;

import camellia.ecommerce.inventory_service.dtos.ProductDto;
import camellia.ecommerce.inventory_service.dtos.ProductSearchQuery;
import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.mappers.ProductMapper;
import camellia.ecommerce.inventory_service.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("search")
    public ResponseEntity<Page<ProductDto>> search(@Valid @ModelAttribute ProductSearchQuery query) {

        Page<ProductDto> products = productService.search(query).map(product -> mapper.toDto(product));

        return ResponseEntity.ok(products);
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
