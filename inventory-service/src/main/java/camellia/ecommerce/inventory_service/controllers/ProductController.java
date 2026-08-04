package camellia.ecommerce.inventory_service.controllers;

import org.springframework.web.bind.annotation.RestController;

import camellia.ecommerce.inventory_service.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

}
