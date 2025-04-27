package com.ecommerce.backend.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ProductVariantDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private Double regularPrice;
    private Double salePrice;
    private Date salePriceFromAt;
    private Date salePriceToAt;
    private int availableItemCount;

    // Key/value attributes for this variant
    private List<ProductVariantAttributeDto> attributes;
}
