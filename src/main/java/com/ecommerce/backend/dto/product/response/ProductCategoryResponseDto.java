package com.ecommerce.backend.dto.product.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductCategoryResponseDto {
    private UUID id;
    private String name;
    private String description;
}

