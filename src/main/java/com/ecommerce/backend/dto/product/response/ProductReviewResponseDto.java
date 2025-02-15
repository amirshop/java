package com.ecommerce.backend.dto.product.response;

import com.ecommerce.backend.dto.account.response.AccountResponseDto;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductReviewResponseDto {
    private UUID id;
    private int rating;
    private String reviewText;
    // You can include a subset of product and reviewer details
    private ProductResponseDto product;
    private AccountResponseDto reviewer;
}
