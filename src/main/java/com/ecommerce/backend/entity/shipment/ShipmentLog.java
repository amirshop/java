package com.ecommerce.backend.entity.shipment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Entity
@DiscriminatorValue("SHIPMENT_LOG")
@Data
public class ShipmentLog extends Shipment {
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    // Possibly track oldStatus or reason for update
}
