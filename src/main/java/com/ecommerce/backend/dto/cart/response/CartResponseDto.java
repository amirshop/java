package com.ecommerce.backend.dto.cart.response;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CartResponseDto {
    private Long id;
    private Date creationDate;
    private Long accountId;
    private List<ItemResponseDto> items;
}
