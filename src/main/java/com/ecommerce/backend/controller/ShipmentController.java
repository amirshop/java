package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.shipment.request.ShipmentRequestDto;
import com.ecommerce.backend.dto.shipment.response.ShipmentResponseDto;
import com.ecommerce.backend.service.shipment.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<List<ShipmentResponseDto>> getAllShipments() {
        List<ShipmentResponseDto> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<ShipmentResponseDto> getShipmentById(@PathVariable Long shipmentId) {
        ShipmentResponseDto shipment = shipmentService.getShipmentById(shipmentId);
        return shipment != null
                ? ResponseEntity.ok(shipment)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ShipmentResponseDto> createShipment(@RequestBody ShipmentRequestDto shipmentRequest) {
        ShipmentResponseDto createdShipment = shipmentService.createShipment(shipmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
    }

    @PutMapping("/{shipmentId}")
    public ResponseEntity<ShipmentResponseDto> updateShipment(@PathVariable Long shipmentId,
                                                              @RequestBody ShipmentRequestDto shipmentRequest) {
        ShipmentResponseDto updatedShipment = shipmentService.updateShipment(shipmentId, shipmentRequest);
        return updatedShipment != null
                ? ResponseEntity.ok(updatedShipment)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long shipmentId) {
        shipmentService.deleteShipment(shipmentId);
        return ResponseEntity.noContent().build();
    }
}

