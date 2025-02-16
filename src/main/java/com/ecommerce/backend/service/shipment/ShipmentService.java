package com.ecommerce.backend.service.shipment;

import com.ecommerce.backend.dto.shipment.request.ShipmentRequestDto;
import com.ecommerce.backend.dto.shipment.response.ShipmentResponseDto;
import com.ecommerce.backend.entity.shipment.Shipment;
import com.ecommerce.backend.repository.shipment.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final ModelMapper modelMapper;

    public List<ShipmentResponseDto> getAllShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();
        return shipments.stream()
                .map(shipment -> modelMapper.map(shipment, ShipmentResponseDto.class))
                .collect(Collectors.toList());
    }

    public ShipmentResponseDto getShipmentById(UUID shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .map(shipment -> modelMapper.map(shipment, ShipmentResponseDto.class))
                .orElse(null);
    }

    public ShipmentResponseDto createShipment(ShipmentRequestDto shipmentRequest) {
        Shipment shipment = modelMapper.map(shipmentRequest, Shipment.class);
        Shipment saved = shipmentRepository.save(shipment);
        return modelMapper.map(saved, ShipmentResponseDto.class);
    }

    public ShipmentResponseDto updateShipment(UUID shipmentId, ShipmentRequestDto shipmentRequest) {
        return shipmentRepository.findById(shipmentId)
                .map(existing -> {
                    existing.setShipmentDate(shipmentRequest.getShipmentDate());
                    existing.setEstimatedArrival(shipmentRequest.getEstimatedArrival());
                    existing.setShipmentMethod(shipmentRequest.getShipmentMethod());
                    existing.setStatus(shipmentRequest.getStatus());
                    Shipment updated = shipmentRepository.save(existing);
                    return modelMapper.map(updated, ShipmentResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteShipment(UUID shipmentId) {
        shipmentRepository.deleteById(shipmentId);
    }
}

