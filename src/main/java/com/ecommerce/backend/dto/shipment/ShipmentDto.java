package com.ecommerce.backend.dto.shipment;

import com.ecommerce.backend.enums.ShipmentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ShipmentDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private Date shipmentDate;
    private Date estimatedArrival;
    private String shipmentMethod;
    private ShipmentStatus status;
}

