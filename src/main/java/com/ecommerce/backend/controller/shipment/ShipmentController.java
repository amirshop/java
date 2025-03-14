package com.ecommerce.backend.controller.shipment;

import com.ecommerce.backend.dto.shipment.ShipmentDto;
import com.ecommerce.backend.service.shipment.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<List<ShipmentDto>> getAllShipments() {
        List<ShipmentDto> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<ShipmentDto> getShipmentById(@PathVariable UUID shipmentId) {
        ShipmentDto shipment = shipmentService.getShipmentById(shipmentId);
        return shipment != null
                ? ResponseEntity.ok(shipment)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ShipmentDto> createShipment(@RequestBody ShipmentDto shipmentRequest) {
        ShipmentDto createdShipment = shipmentService.createShipment(shipmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
    }

    @PutMapping("/{shipmentId}")
    public ResponseEntity<ShipmentDto> updateShipment(@PathVariable UUID shipmentId,
                                                              @RequestBody ShipmentDto shipmentRequest) {
        ShipmentDto updatedShipment = shipmentService.updateShipment(shipmentId, shipmentRequest);
        return updatedShipment != null
                ? ResponseEntity.ok(updatedShipment)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<Void> deleteShipment(@PathVariable UUID shipmentId) {
        shipmentService.deleteShipment(shipmentId);
        return ResponseEntity.noContent().build();
    }
}

