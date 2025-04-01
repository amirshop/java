package com.ecommerce.backend.service.customer;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.dto.customer.CustomerProfileDto;
import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.entity.customer.CustomerProfile;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.customer.CustomerProfileMapper;
import com.ecommerce.backend.repository.customer.CustomerProfileRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerProfileService extends BaseService<CustomerProfile, CustomerProfileDto> {

    private final CustomerProfileRepository profileRepository;
    private final CustomerProfileMapper customerProfileMapper;
    private final CustomerService customerService;

    public CustomerProfileService(CustomerProfileRepository profileRepository,
                                  CustomerProfileMapper customerProfileMapper,
                                  CustomerService customerService) {
        super(profileRepository, customerProfileMapper::toDto);
        this.profileRepository = profileRepository;
        this.customerProfileMapper = customerProfileMapper;
        this.customerService = customerService;
    }

    public List<CustomerProfileDto> getAllCustomerProfiles() {
        List<CustomerProfile> customerProfiles = profileRepository.findAll();
        return customerProfiles.stream()
                .map(customerProfileMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerProfileDto getCustomerProfileById(UUID customerProfileId) {
        return profileRepository.findById(customerProfileId)
                .map(customerProfileMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerProfile", "id", customerProfileId.toString()));
    }

    public CustomerProfileDto createCustomerProfile(CustomerProfileDto customerProfileRequest) {
        Customer customer = customerService.findById(customerProfileRequest.getCustomerId());
        checkExistCustomer(customerProfileRequest.getCustomerId());
        CustomerProfile customerProfile = customerProfileMapper.toEntity(customerProfileRequest);
        customerProfile.setCustomer(customer);
        customerProfile.setCreatedAt(new Date());
        customerProfile.setUpdatedAt(new Date());
        CustomerProfile savedCustomerProfile = profileRepository.save(customerProfile);
        return customerProfileMapper.toDto(savedCustomerProfile);
    }

    private void checkExistCustomer(UUID customerId) {
        if (profileRepository.existsByCustomerId(customerId)) {
            throw new ResourceAlreadyExistsException("Customer id in Customer Profile already exists");
        }
    }

    public CustomerProfileDto updateCustomerProfile(UUID id, CustomerProfileDto updatedProfile) {
        return profileRepository.findById(id)
                .map(profile -> {
                    Optional.ofNullable(updatedProfile.getBirthDate())
                            .ifPresent(profile::setBirthDate);
                    Optional.ofNullable(updatedProfile.getProfilePictureUrl())
                            .filter(profilePictureUrl -> !profilePictureUrl.isBlank())
                            .ifPresent(profile::setProfilePictureUrl);
                    Optional.ofNullable(updatedProfile.getGender()).ifPresent(profile::setGender);
                    profile.setUpdatedAt(new Date());
                    CustomerProfile savedCustomerProfile = profileRepository.save(profile);
                CustomerProfile savedProfile = profileRepository.save(savedCustomerProfile);
                return customerProfileMapper.toDto(savedProfile);
        }).orElseThrow(() -> new RuntimeException("CustomerProfile not found with id " + id));
    }

    public void deleteCustomerProfile(UUID id) {
        profileRepository.deleteById(id);
    }

    @Override
    protected Specification<CustomerProfile> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchCustomerProfiles(SearchDto requestDto) {
        return search(requestDto, CustomerProfileDto.class);
    }
}

