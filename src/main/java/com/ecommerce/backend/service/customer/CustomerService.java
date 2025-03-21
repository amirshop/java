package com.ecommerce.backend.service.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(UUID id, Customer customerDetails) {
        return customerRepository.findById(id).map(customer -> {
            customer.setEmail(customerDetails.getEmail());
            customer.setPhone(customerDetails.getPhone());
            customer.setPassword(customerDetails.getPassword());
            customer.setFirstName(customerDetails.getFirstName());
            customer.setLastName(customerDetails.getLastName());
            // update additional fields if needed
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
    }

    public void delete(UUID id) {
        customerRepository.deleteById(id);
    }
}
