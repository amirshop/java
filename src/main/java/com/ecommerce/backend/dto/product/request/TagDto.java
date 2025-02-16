package com.ecommerce.backend.dto.product.request;

import lombok.Data;

import java.util.UUID;

@Data
public class TagDto {
    private UUID id;
    private String name;
    private String slug;
    private String description;
}

