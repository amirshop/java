package com.ecommerce.backend.dto.shipment.request;

import com.ecommerce.backend.enums.ShipmentStatus;
import lombok.Data;
import java.util.Date;

@Data
public class ShipmentRequestDto {
    private Date shipmentDate;
    private Date estimatedArrival;
    private String shipmentMethod;
    private ShipmentStatus status; // e.g., PENDING, SHIPPED, etc.
}
