package com.ecommerce.backend.dto.product.request;

import lombok.Data;

@Data
public class ProductRequestDto {
    private String name;
    private String description;
    private double price;
    private int availableItemCount;
    // Reference to a category by its ID
    private Long categoryId;
}

