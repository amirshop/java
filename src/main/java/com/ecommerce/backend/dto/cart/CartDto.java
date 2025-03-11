package com.ecommerce.backend.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private Date creationDate;
    private Long accountId;
    private List<UUID> items;
}
