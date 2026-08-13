package camellia.ecommerce.inventory_service.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import camellia.ecommerce.inventory_service.dtos.ProductImageMetadata;
import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/product-image")
public class ProductImageController {

    @Value("${app.product-images-dir}")
    private String productImagesDir;

    private final ProductService productService;

    @PostMapping(name = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProductImage(@RequestPart ProductImageMetadata metadata,
            @RequestPart MultipartFile image) {
        Path imagesDir = Paths.get(productImagesDir).toAbsolutePath().normalize();
        Path imagePath = imagesDir.resolve(metadata.imageName()).normalize();

        Product product = productService.findByPublicId(metadata.productId());
        product.setImagePath(imagePath.toString());

        productService.save(product);

        try {
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
