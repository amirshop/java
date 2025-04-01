package com.ecommerce.backend.service.customer;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.customer.CustomerAddressDto;
import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.entity.customer.CustomerAddress;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.customer.CustomerAddressMapper;
import com.ecommerce.backend.repository.customer.CustomerAddressRepository;
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
public class CustomerAddressService extends BaseService<CustomerAddress, CustomerAddressDto> {

    private final CustomerAddressRepository addressRepository;
    private final CustomerAddressMapper customerAddressMapper;
    private final CustomerService customerService;

    public CustomerAddressService(CustomerAddressRepository addressRepository,
                                  CustomerAddressMapper customerAddressMapper, CustomerService customerService) {
        super(addressRepository, customerAddressMapper::toDto);
        this.addressRepository = addressRepository;
        this.customerAddressMapper = customerAddressMapper;
        this.customerService = customerService;
    }

    public List<CustomerAddressDto> getAllCustomerAddresses() {
        List<CustomerAddress> customerAddresses = addressRepository.findAll();
        return customerAddresses.stream()
                .map(customerAddressMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerAddressDto getCustomerAddressById(UUID id) {
        return addressRepository.findById(id)
                .map(customerAddressMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("customer", "id", id.toString()));
    }


    public CustomerAddressDto createCustomerAddress(CustomerAddressDto request) {
        Customer customer = customerService.findById(request.getCustomerId());
        checkExistCustomer(request.getCustomerId());
        CustomerAddress customerAddress = customerAddressMapper.toEntity(request);
        customerAddress.setCustomer(customer);
        customerAddress.setCreatedAt(new Date());
        customerAddress.setUpdatedAt(new Date());
        CustomerAddress savedAddress = addressRepository.save(customerAddress);
        return customerAddressMapper.toDto(savedAddress);
    }

    private void checkExistCustomer(UUID customerId) {
        if (addressRepository.existsByCustomerId(customerId)) {
            throw new ResourceAlreadyExistsException("Customer id in Customer Address already exists");
        }
    }

    public CustomerAddressDto updateCustomerAddress(UUID id, CustomerAddressDto request) {
        return addressRepository.findById(id).map(address -> {
            Optional.ofNullable(request.getPhone())
                    .filter(phone -> !phone.isBlank())
                    .ifPresent(address::setPhone);
            Optional.ofNullable(request.getCountry())
                    .filter(country -> !country.isBlank())
                    .ifPresent(address::setCountry);
            Optional.ofNullable(request.getCity())
                    .filter(city -> !city.isBlank())
                    .ifPresent(address::setCity);
            Optional.ofNullable(request.getStreet())
                    .filter(street -> !street.isBlank())
                    .ifPresent(address::setStreet);
            Optional.ofNullable(request.getPostalCode())
                    .filter(postalCode -> !postalCode.isBlank())
                    .ifPresent(address::setPostalCode);
            Optional.ofNullable(request.getState())
                    .filter(state -> !state.isBlank())
                    .ifPresent(address::setState);
            Optional.ofNullable(request.getIsDefault())
                    .ifPresent(address::setIsDefault);
            address.setUpdatedAt(new Date());
            CustomerAddress savedAddress = addressRepository.save(address);
            return customerAddressMapper.toDto(savedAddress);
        }).orElseThrow(() -> new RuntimeException("CustomerAddress not found with id " + id));
    }

    public void deleteCustomerAddress(UUID id) {
        addressRepository.deleteById(id);
    }

    @Override
    protected Specification<CustomerAddress> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchCustomerAddresses(SearchDto requestDto) {
        return search(requestDto, CustomerAddressDto.class);
    }
}

