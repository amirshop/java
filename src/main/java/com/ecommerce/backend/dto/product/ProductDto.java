package com.ecommerce.backend.dto.product;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @AllowedFilter
    private String name;

    private String slug;
    private String description;

    @AllowedFilter
    private double price;

    @AllowedFilter
    private int availableItemCount;

    @AllowedFilter
    private ProductCategoryDto category;


    private List<UUID> productReviews;
    private Set<TagDto> tags;
}
