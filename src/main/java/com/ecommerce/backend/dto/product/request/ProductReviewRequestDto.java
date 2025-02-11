package com.ecommerce.backend.dto.product.request;

import lombok.Data;

@Data
public class ProductReviewRequestDto {
    private int rating;
    private String reviewText;
    private Long productId;
    private Long reviewerId;
}

