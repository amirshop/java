package com.ecommerce.backend.controller.customer;

import java.util.List;
import java.util.UUID;

import com.ecommerce.backend.entity.customer.CustomerProfile;
import com.ecommerce.backend.service.customer.CustomerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer-profiles")
public class CustomerProfileController {

    @Autowired
    private CustomerProfileService profileService;

    @GetMapping
    public List<CustomerProfile> getAll() {
        return profileService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerProfile getById(@PathVariable UUID id) {
        return profileService.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerProfile not found with id " + id));
    }

    @PostMapping
    public CustomerProfile create(@RequestBody CustomerProfile profile) {
        return profileService.create(profile);
    }

    @PutMapping("/{id}")
    public CustomerProfile update(@PathVariable UUID id, @RequestBody CustomerProfile profile) {
        return profileService.update(id, profile);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        profileService.delete(id);
    }
}

