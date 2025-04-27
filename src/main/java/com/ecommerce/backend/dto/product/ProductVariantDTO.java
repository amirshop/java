package com.ecommerce.backend.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ProductVariantDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private Double regularPrice;
    private Double salePrice;
    private Date salePriceFromAt;
    private Date salePriceToAt;
    private int availableItemCount;
    private String variantName;
    private String variantValue;
}
