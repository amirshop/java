package com.ecommerce.backend.controller.customer;

import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.customer.CustomerAddressDto;
import com.ecommerce.backend.service.customer.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer-addresses")
@RequiredArgsConstructor
public class CustomerAddressController {

    private final CustomerAddressService addressService;

    @GetMapping
    public ResponseEntity<List<CustomerAddressDto>> getAllCustomerAddresses() {
        List<CustomerAddressDto> customerAddresses = addressService.getAllCustomerAddresses();
        return ResponseEntity.ok(customerAddresses);
    }

    @GetMapping("/{customerAddressId}")
    public CustomerAddressDto getCustomerAddressById(@PathVariable UUID customerAddressId) {
        return addressService.getCustomerAddressById(customerAddressId);
    }

    @PostMapping
    public ResponseEntity<CustomerAddressDto> createCustomerAddress(@RequestBody CustomerAddressDto customerAddressRequest) {
        CustomerAddressDto createdCustomerAddress = addressService.createCustomerAddress(customerAddressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerAddress);
    }

    @PutMapping("/{customerAddressId}")
    public ResponseEntity<CustomerAddressDto> updateCustomerAddress(@PathVariable UUID customerAddressId,
                                                                    @RequestBody CustomerAddressDto customerAddressRequest) {
        CustomerAddressDto updatedCustomerAddress = addressService.updateCustomerAddress(customerAddressId, customerAddressRequest);
        return updatedCustomerAddress != null
                ? ResponseEntity.ok(updatedCustomerAddress)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{customerAddressId}")
    public ResponseEntity<Void> deleteCustomerAddress(@PathVariable UUID customerAddressId) {
        addressService.deleteCustomerAddress(customerAddressId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseDto searchCustomerAddresses(@RequestBody SearchDto requestDto) {
        return addressService.searchCustomerAddresses(requestDto);
    }
}

