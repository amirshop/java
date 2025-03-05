package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.dto.account.response.AccountResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductReviewDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private int rating;
    private String reviewText;
    // You can include a subset of product and reviewer details
    private ProductDto product;
    private AccountResponseDto reviewer;
}
