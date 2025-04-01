package com.ecommerce.backend.service.customer;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.customer.CustomerMapper;
import com.ecommerce.backend.repository.customer.CustomerRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService extends BaseService<Customer, CustomerDto> {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper, PasswordEncoder passwordEncoder) {
        super(customerRepository, customerMapper::toDto);
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
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

    public Customer findById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId.toString()));
    }

    public CustomerDto createCustomer(CustomerDto customerRequest) {

        // Check if email already exists
        checkExistEmail(customerRequest.getEmail());

        // Check if username already exists
        checkExistUsername(customerRequest.getUsername());

        // Check if phone already exists
        checkExistPhone(customerRequest.getPhone());

        Customer customer = customerMapper.toEntity(customerRequest);
        customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        customer.setCreatedAt(new Date());
        customer.setUpdatedAt(new Date());
        customer.setEmailVerified(false);
        customer.setPhoneVerified(false);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }

    private void checkExistPhone(String phone) {
        if (customerRepository.existsByPhone(phone)) {
            throw new ResourceAlreadyExistsException("phone already exists");
        }
    }

    private void checkExistUsername(String username) {
        if (customerRepository.existsByUsername(username)) {
            throw new ResourceAlreadyExistsException("username already exists");
        }
    }

    private void checkExistEmail(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("email already exists");
        }
    }

    public CustomerDto updateCustomer(UUID customerId, CustomerDto customerRequest) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    Optional.ofNullable(customerRequest.getUsername())
                            .filter(username -> !username.isBlank())
                            .ifPresent(customer::setUsername);
                    Optional.ofNullable(customerRequest.getEmail())
                            .filter(email -> !email.isBlank())
                            .ifPresent(customer::setEmail);
                    Optional.ofNullable(customerRequest.getPhone())
                            .filter(phone -> !phone.isBlank())
                            .ifPresent(customer::setPhone);
                    Optional.ofNullable(customerRequest.getFirstname())
                            .filter(firstname -> !firstname.isBlank())
                            .ifPresent(customer::setFirstname);
                    Optional.ofNullable(customerRequest.getLastname())
                            .filter(lastname -> !lastname.isBlank())
                            .ifPresent(customer::setLastname);
                    Optional.ofNullable(customerRequest.getPassword())
                            .filter(password -> !password.isBlank())
                            .ifPresent(password -> customer.setPassword(passwordEncoder.encode(password)));
                    customer.setUpdatedAt(new Date());
                    Customer updated = customerRepository.save(customer);
                    return customerMapper.toDto(updated);
        }).orElseThrow(() -> new ResourceNotFoundException("customer", "id", customerId.toString()));
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
