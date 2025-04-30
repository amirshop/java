package com.ecommerce.backend.dto.customer;

import com.ecommerce.backend.annotaions.AllowedFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import java.util.Date;
import java.util.UUID;

@Data
public class WishlistItemDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @AllowedFilter
    private UUID id;

    @AllowedFilter
    private UUID customerId;

    @AllowedFilter
    private UUID productId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @AllowedFilter
    private Date addedAt;
}
