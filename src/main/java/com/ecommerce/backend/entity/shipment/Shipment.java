package com.ecommerce.backend.entity.shipment;

import com.ecommerce.backend.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "shipment_type", discriminatorType = DiscriminatorType.STRING)
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date shipmentDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedArrival;

    private String shipmentMethod;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
}