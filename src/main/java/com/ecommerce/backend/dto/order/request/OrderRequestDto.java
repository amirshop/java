package com.ecommerce.backend.dto.order.request;

import com.ecommerce.backend.dto.cart.request.ItemRequestDto;
import com.ecommerce.backend.enums.OrderStatus;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderRequestDto {
    private String orderNumber;
    private String orderProcessor;
    private OrderStatus status; // e.g., UNSHIPPED, PENDING, etc.
    private Date orderDate;
    private List<ItemRequestDto> items;
}
