package camellia.ecommerce.inventory_service.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import camellia.ecommerce.inventory_service.enums.ImageSize;

@Component
public class StringToImageSizeConverter implements Converter<String, ImageSize> {

    @Override
    public ImageSize convert(String value) {
        return ImageSize.fromValue(value);
    }

}
