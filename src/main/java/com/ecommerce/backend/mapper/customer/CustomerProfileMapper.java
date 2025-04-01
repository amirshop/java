package com.ecommerce.backend.mapper.customer;

import com.ecommerce.backend.dto.customer.CustomerProfileDto;
import com.ecommerce.backend.entity.customer.CustomerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerProfileMapper {

    CustomerProfile toEntity(CustomerProfileDto customerProfileDto);

    @Mapping(target = "customerId", source = "customer.id")
    CustomerProfileDto toDto(CustomerProfile customerProfile);
}
