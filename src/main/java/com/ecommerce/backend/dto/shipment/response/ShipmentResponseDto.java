package com.ecommerce.backend.dto.shipment.response;

import lombok.Data;
import java.util.Date;

@Data
public class ShipmentResponseDto {
    private Long id;
    private Date shipmentDate;
    private Date estimatedArrival;
    private String shipmentMethod;
    private String status;
}

