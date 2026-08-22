package camellia.ecommerce.inventory_service.enums;

public enum ImageSize {
    SMALL("small"), MEDIUM("medium"), FULL("full");

    private String size;

    private ImageSize(String size) {
        this.size = size;
    }

    public static ImageSize fromValue(String value) {
        for (ImageSize candidate : values()) {
            if (candidate.size.equalsIgnoreCase(value))
                return candidate;
        }
        throw new IllegalArgumentException("Unknown image size: " + value);
    }

    @Override
    public String toString() {
        return this.size;
    }
}
