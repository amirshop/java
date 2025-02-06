package com.ecommerce.backend.service.account;

import com.ecommerce.backend.entity.account.Customer;
import com.ecommerce.backend.repository.account.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    public List<Customer> getAllCustomers() { return customerRepository.findAll(); }
}
