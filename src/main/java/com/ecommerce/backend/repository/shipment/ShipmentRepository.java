package com.ecommerce.backend.repository.shipment;

import com.ecommerce.backend.entity.shipment.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
}
