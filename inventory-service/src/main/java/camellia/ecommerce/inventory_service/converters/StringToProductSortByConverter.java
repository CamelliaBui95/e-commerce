package camellia.ecommerce.inventory_service.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import camellia.ecommerce.inventory_service.enums.ProductSortBy;

@Component
public class StringToProductSortByConverter implements Converter<String, ProductSortBy> {

    @Override
    public ProductSortBy convert(String value) {
        if (value == null || value.isBlank())
            return null;

        return ProductSortBy.fromValue(value);
    }

}
