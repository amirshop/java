package com.ecommerce.backend.dto.shipment;

import com.ecommerce.backend.dto.shipment.response.ShipmentResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShipmentLogDto extends ShipmentResponseDto {
    private Date updatedAt;
}

