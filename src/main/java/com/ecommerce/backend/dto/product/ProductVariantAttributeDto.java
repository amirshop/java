package com.ecommerce.backend.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductVariantAttributeDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String attributeName;
    private String attributeValue;
}
