package com.ecommerce.backend.dto.product.response;

import com.ecommerce.backend.dto.account.response.AccountResponseDto;
import lombok.Data;

@Data
public class ProductReviewResponseDto {
    private Long id;
    private int rating;
    private String reviewText;
    // You can include a subset of product and reviewer details
    private ProductResponseDto product;
    private AccountResponseDto reviewer;
}
