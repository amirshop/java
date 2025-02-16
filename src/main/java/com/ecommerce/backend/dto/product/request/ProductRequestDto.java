package com.ecommerce.backend.dto.product.request;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ProductRequestDto {
    private String name;
    private String slug;
    private String description;
    private double price;
    private int availableItemCount;
    private UUID categoryId;
    private Set<UUID> tagIds;
}

