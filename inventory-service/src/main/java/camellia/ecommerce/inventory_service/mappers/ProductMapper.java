package camellia.ecommerce.inventory_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import camellia.ecommerce.inventory_service.dtos.ProductDto;
import camellia.ecommerce.inventory_service.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    @Mapping(target = "publicId", ignore = true)
    Product toEntity(ProductDto productDto);
}
