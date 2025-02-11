package com.ecommerce.backend.dto.order;

import com.ecommerce.backend.dto.order.response.OrderResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderLogDto extends OrderResponseDto {
    private Date updatedAt;
}
