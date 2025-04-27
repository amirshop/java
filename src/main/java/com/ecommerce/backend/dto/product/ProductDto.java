package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.ecommerce.backend.entity.product.ProductVariant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @AllowedFilter
    private UUID id;

    @AllowedFilter
    private String name;

    @AllowedFilter
    private String slug;

    private String description;

    @AllowedFilter
    private double price;

    @AllowedFilter
    private int availableItemCount;

    private Set<UUID> categories;
    private List<UUID> productReviews;
    private Set<UUID> tags;

    // Variants for this product
    private List<ProductVariantDto> variants;
}
