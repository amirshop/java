package com.ecommerce.backend.dto.product.response;

import com.ecommerce.backend.dto.product.request.TagDto;
import lombok.Data;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductResponseDto {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private double price;
    private int availableItemCount;
    private ProductCategoryResponseDto category;
    private List<ProductReviewResponseDto> productReviews;
    private Set<TagDto> tags;
}

