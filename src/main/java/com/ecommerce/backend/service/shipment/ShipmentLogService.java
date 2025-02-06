package com.ecommerce.backend.service.shipment;

import com.ecommerce.backend.entity.shipment.ShipmentLog;
import com.ecommerce.backend.repository.shipment.ShipmentLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentLogService {
    private final ShipmentLogRepository shipmentLogRepository;
    public List<ShipmentLog> getAllShipmentLogs() { return shipmentLogRepository.findAll(); }
}
