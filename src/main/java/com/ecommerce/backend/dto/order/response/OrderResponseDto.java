package com.ecommerce.backend.dto.order.response;

import com.ecommerce.backend.dto.shipment.response.ShipmentResponseDto;
import com.ecommerce.backend.dto.cart.response.ItemResponseDto;
import com.ecommerce.backend.dto.payment.response.PaymentResponseDto;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private String orderNumber;
    private String orderProcessor;
    private String status;
    private Date orderDate;
    private List<PaymentResponseDto> payments;
    private List<ShipmentResponseDto> shipments;
    private List<ItemResponseDto> items;
}

