package com.ecommerce.backend.mapper.customer;

import com.ecommerce.backend.dto.customer.CustomerProfileDto;
import com.ecommerce.backend.entity.customer.CustomerProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerProfileMapper {

    CustomerProfile toEntity(CustomerProfileDto customerProfileDto);
    CustomerProfileDto toDto(CustomerProfile customerProfile);
}
