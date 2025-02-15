package com.ecommerce.backend.dto.shipment.response;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class ShipmentResponseDto {
    private UUID id;
    private Date shipmentDate;
    private Date estimatedArrival;
    private String shipmentMethod;
    private String status;
}

