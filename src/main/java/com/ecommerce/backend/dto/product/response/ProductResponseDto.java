package com.ecommerce.backend.dto.product.response;

import lombok.Data;
import java.util.List;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int availableItemCount;
    private ProductCategoryResponseDto category;
    private List<ProductReviewResponseDto> productReviews;
}

