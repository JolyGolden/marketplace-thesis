package nazar.zhanabergenov.shop_monolith.product.dto;

import java.math.BigDecimal;

public record ProductRequest(
    String name,
    String description,
    BigDecimal price,
    Integer stockQuantity
) {
    public ProductRequest {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The price must be positive");
        }
        if (stockQuantity == null || stockQuantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive or zero");
        }
    }
}
