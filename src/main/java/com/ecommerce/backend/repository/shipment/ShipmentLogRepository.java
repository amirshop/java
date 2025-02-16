package com.ecommerce.backend.repository.shipment;

import com.ecommerce.backend.entity.shipment.ShipmentLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipmentLogRepository extends JpaRepository<ShipmentLog, UUID> {
}
