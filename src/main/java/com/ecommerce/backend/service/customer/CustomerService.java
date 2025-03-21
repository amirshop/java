package com.ecommerce.backend.service.customer;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.customer.CustomerMapper;
import com.ecommerce.backend.repository.customer.CustomerRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService extends BaseService<Customer, CustomerDto> {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper) {
        super(customerRepository, customerMapper::toDto);
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId.toString()));
    }

    public CustomerDto createCustomer(CustomerDto customerRequest) {
        Customer customer = customerMapper.toEntity(customerRequest);
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }

    public CustomerDto updateCustomer(UUID customerId, CustomerDto customerRequest) {
        return customerRepository.findById(customerId)
                .map(existing -> {
                    existing.setEmail(customerRequest.getEmail());
                    existing.setPhone(customerRequest.getPhone());
                    //TODO:add passwordEncoder
                    existing.setPassword(customerRequest.getPassword());
                    existing.setFirstName(customerRequest.getFirstName());
                    existing.setLastName(customerRequest.getLastName());
                    existing.setUpdatedAt(new Date());
                    Customer updated = customerRepository.save(existing);
                    return customerMapper.toDto(updated);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }

    @Override
    protected Specification<Customer> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchCustomers(SearchDto requestDto) {
        return search(requestDto, CustomerDto.class);
    }
}
