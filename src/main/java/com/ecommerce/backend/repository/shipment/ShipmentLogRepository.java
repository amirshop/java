package com.ecommerce.backend.repository.shipment;

import com.ecommerce.backend.entity.shipment.ShipmentLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentLogRepository extends JpaRepository<ShipmentLog, Long> {
}
