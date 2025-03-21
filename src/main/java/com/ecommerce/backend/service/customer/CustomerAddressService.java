package com.ecommerce.backend.service.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ecommerce.backend.entity.customer.CustomerAddress;
import com.ecommerce.backend.repository.customer.CustomerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerAddressService {

    @Autowired
    private CustomerAddressRepository addressRepository;

    public List<CustomerAddress> findAll() {
        return addressRepository.findAll();
    }

    public Optional<CustomerAddress> findById(UUID id) {
        return addressRepository.findById(id);
    }

    public CustomerAddress create(CustomerAddress address) {
        return addressRepository.save(address);
    }

    public CustomerAddress update(UUID id, CustomerAddress addressDetails) {
        return addressRepository.findById(id).map(address -> {
            address.setCustomer(addressDetails.getCustomer());
            address.setCountry(addressDetails.getCountry());
            address.setCity(addressDetails.getCity());
            address.setStreet(addressDetails.getStreet());
            address.setPostalCode(addressDetails.getPostalCode());
            address.setPhoneNumber(addressDetails.getPhoneNumber());
            address.setDefault(addressDetails.isDefault());
            return addressRepository.save(address);
        }).orElseThrow(() -> new RuntimeException("CustomerAddress not found with id " + id));
    }

    public void delete(UUID id) {
        addressRepository.deleteById(id);
    }
}

