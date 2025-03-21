package com.ecommerce.backend.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class WishlistItemDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private UUID customerId;

    private UUID productId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date addedAt;
}
