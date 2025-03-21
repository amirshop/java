package com.ecommerce.backend.mapper.customer;

import com.ecommerce.backend.dto.customer.CustomerDto;
import com.ecommerce.backend.entity.customer.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerDto customerDto);
    CustomerDto toDto(Customer customer);
}
