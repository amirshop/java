package com.ecommerce.backend.controller.customer;

import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.customer.CustomerProfileDto;
import com.ecommerce.backend.entity.customer.CustomerProfile;
import com.ecommerce.backend.service.customer.CustomerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer-profiles")
@RequiredArgsConstructor
public class CustomerProfileController {

    private final CustomerProfileService profileService;

    @GetMapping
    public ResponseEntity<List<CustomerProfileDto>> getAllCustomerProfiles() {
        List<CustomerProfileDto> customerProfiles = profileService.getAllCustomerProfiles();
        return ResponseEntity.ok(customerProfiles);
    }

    @GetMapping("/{customerProfileId}")
    public CustomerProfileDto getCustomerProfileById(@PathVariable UUID customerProfileId) {
        return profileService.getCustomerProfileById(customerProfileId);
    }

    @PostMapping
    public ResponseEntity<CustomerProfileDto> create(@RequestBody CustomerProfileDto profileRequest) {
        CustomerProfileDto createdCustomerProfile = profileService.createCustomerProfile(profileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerProfile);
    }

    @PutMapping("/{customerProfileId}")
    public CustomerProfileDto update(@PathVariable UUID customerProfileId, @RequestBody CustomerProfileDto profile) {
        return profileService.updateCustomerProfile(customerProfileId, profile);
    }

    @DeleteMapping("/{customerProfileId}")
    public ResponseEntity<Void> delete(@PathVariable UUID customerProfileId) {
        profileService.deleteCustomerProfile(customerProfileId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseDto searchCustomerProfiles(@RequestBody SearchDto requestDto) {
        return profileService.searchCustomerProfiles(requestDto);
    }
}

