package com.ecommerce.backend.dto.order.request;

import com.ecommerce.backend.dto.cart.request.ItemRequestDto;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderRequestDto {
    private String orderNumber;
    private String orderProcessor;
    private String status; // e.g., UNSHIPPED, PENDING, etc.
    private Date orderDate;
    private List<ItemRequestDto> items;
}
