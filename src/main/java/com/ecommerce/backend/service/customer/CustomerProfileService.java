package com.ecommerce.backend.service.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ecommerce.backend.entity.customer.CustomerProfile;
import com.ecommerce.backend.repository.customer.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerProfileService {

    @Autowired
    private CustomerProfileRepository profileRepository;

    public List<CustomerProfile> findAll() {
        return profileRepository.findAll();
    }

    public Optional<CustomerProfile> findById(UUID id) {
        return profileRepository.findById(id);
    }

    public CustomerProfile create(CustomerProfile profile) {
        return profileRepository.save(profile);
    }

    public CustomerProfile update(UUID id, CustomerProfile profileDetails) {
        return profileRepository.findById(id).map(profile -> {
            profile.setCustomer(profileDetails.getCustomer());
            profile.setGender(profileDetails.getGender());
            profile.setBirthDate(profileDetails.getBirthDate());
            profile.setProfilePictureUrl(profileDetails.getProfilePictureUrl());
            // update timestamps or additional fields if needed
            return profileRepository.save(profile);
        }).orElseThrow(() -> new RuntimeException("CustomerProfile not found with id " + id));
    }

    public void delete(UUID id) {
        profileRepository.deleteById(id);
    }
}

