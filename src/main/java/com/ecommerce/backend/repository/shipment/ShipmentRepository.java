package com.ecommerce.backend.repository.shipment;

import com.ecommerce.backend.entity.shipment.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
