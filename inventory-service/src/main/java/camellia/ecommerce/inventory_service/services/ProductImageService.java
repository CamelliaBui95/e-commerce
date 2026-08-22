package camellia.ecommerce.inventory_service.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import camellia.ecommerce.inventory_service.enums.ImageSize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageService {

    @Value("${app.product-images-dir}")
    private String productImagesDir;

    public Path getImage(String imageName, ImageSize size) throws IOException, ResourceNotFoundException {
        Path imagesDir = Paths.get(productImagesDir).normalize();

        String fullImageName = size.equals(ImageSize.FULL) ? imageName : getFullImageName(imageName, size);
        Path resolved = imagesDir.resolve(fullImageName).normalize();

        boolean fileExists = resolved.startsWith(imagesDir) && Files.isRegularFile(resolved);
        if (size.equals(ImageSize.FULL) && !fileExists) {
            throw new ResourceNotFoundException(String.format("%s not found.", fullImageName));
        } else if (!fileExists) {
            File originalFile = imagesDir.resolve(imageName).normalize().toFile();
            return resizeImage(originalFile, size);
        }

        return resolved;
    }

    public Path resizeImage(File image, ImageSize size) throws IOException {
        BufferedImage resizedImage = resizeImage(ImageIO.read(image), size);

        Path imageDir = Paths.get(productImagesDir).normalize();
        Path resizedImagePath = imageDir.resolve(getFullImageName(image.getName(), size));

        ImageIO.write(resizedImage, FilenameUtils.getExtension(image.getName()), resizedImagePath.toFile());

        return resizedImagePath;
    }

    public BufferedImage resizeImage(BufferedImage image, ImageSize size) {
        double scale = getScale(size);

        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);

        BufferedImage output = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        output.getGraphics().drawImage(resizedImage, 0, 0, null);

        return output;
    }

    private double getScale(ImageSize size) {
        switch (size) {
        case ImageSize.SMALL:
            return 0.3;
        case ImageSize.MEDIUM:
            return 0.6;
        default:
            return 1.0;
        }
    }

    private String getFullImageName(String imageName, ImageSize size) {
        int dotIndex = imageName.lastIndexOf('.');
        String prefix = imageName.substring(0, dotIndex);
        String suffix = imageName.substring(dotIndex + 1);

        return String.format("%s-%s.%s", prefix, size.toString(), suffix);
    }

}
