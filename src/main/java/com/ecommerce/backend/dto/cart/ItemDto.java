package com.ecommerce.backend.dto.cart;

import com.ecommerce.backend.dto.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private int quantity;
    private double price;
    private ProductDto product;
}

