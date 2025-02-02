package com.ecommerce.backend.entity;

import com.ecommerce.backend.enums.ShipmentStatus;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date shipmentDate;
    private Date estimatedArrival;
    private String shipmentMethod;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
}
