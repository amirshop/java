package com.ecommerce.backend.dto.shipment.request;

import lombok.Data;
import java.util.Date;

@Data
public class ShipmentRequestDto {
    private Date shipmentDate;
    private Date estimatedArrival;
    private String shipmentMethod;
    private String status; // e.g., PENDING, SHIPPED, etc.
}
