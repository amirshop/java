package com.ecommerce.backend.controller.controller;

import java.util.List;
import java.util.UUID;

import com.ecommerce.backend.entity.customer.CustomerAddress;
import com.ecommerce.backend.service.customer.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer-addresses")
public class CustomerAddressController {

    @Autowired
    private CustomerAddressService addressService;

    @GetMapping
    public List<CustomerAddress> getAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerAddress getById(@PathVariable UUID id) {
        return addressService.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerAddress not found with id " + id));
    }

    @PostMapping
    public CustomerAddress create(@RequestBody CustomerAddress address) {
        return addressService.create(address);
    }

    @PutMapping("/{id}")
    public CustomerAddress update(@PathVariable UUID id, @RequestBody CustomerAddress address) {
        return addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        addressService.delete(id);
    }
}

