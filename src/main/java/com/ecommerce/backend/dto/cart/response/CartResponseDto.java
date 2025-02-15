package com.ecommerce.backend.dto.cart.response;

import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CartResponseDto {
    private UUID id;
    private Date creationDate;
    private Long accountId;
    private List<ItemResponseDto> items;
}
