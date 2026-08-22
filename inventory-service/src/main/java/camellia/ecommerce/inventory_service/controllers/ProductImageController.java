package camellia.ecommerce.inventory_service.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import camellia.ecommerce.inventory_service.dtos.ProductImageMetadata;
import camellia.ecommerce.inventory_service.entities.Product;
import camellia.ecommerce.inventory_service.enums.ImageSize;
import camellia.ecommerce.inventory_service.services.ProductImageService;
import camellia.ecommerce.inventory_service.services.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
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

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/product-image")
public class ProductImageController {

    @Value("${app.product-images-dir}")
    private String productImagesDir;

    private final ProductService productService;

    private final ProductImageService productImageService;

    @PostMapping(name = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProductImage(
            @Parameter(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductImageMetadata.class))) @RequestPart ProductImageMetadata metadata,
            @RequestPart MultipartFile image) {
        Path imagesDir = Paths.get(productImagesDir).toAbsolutePath().normalize();

        String imageName = metadata.imageName() != null ? metadata.imageName() : image.getOriginalFilename();
        Path imagePath = imagesDir.resolve(imageName).normalize();

        try {
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            Product product = productService.findByPublicId(metadata.productId());
            product.setImageName(imageName);

            productService.save(product);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping
    public ResponseEntity<Resource> getProductImage(@RequestParam String imageName,
            @RequestParam(defaultValue = "small") ImageSize size) {

        try {
            Path resolved = productImageService.getImage(imageName, size);
            Resource image = new FileSystemResource(resolved);
            MediaType contentType = MediaTypeFactory.getMediaType(image).orElse(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok().contentType(contentType).cacheControl(CacheControl.noCache()).body(image);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

}
