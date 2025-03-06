package com.ecommerce.backend.dto.shipment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShipmentLogDto extends ShipmentDto {
    private Date updatedAt;
}

