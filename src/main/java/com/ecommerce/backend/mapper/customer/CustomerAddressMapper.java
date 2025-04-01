package com.ecommerce.backend.mapper.customer;

import com.ecommerce.backend.dto.customer.CustomerAddressDto;
import com.ecommerce.backend.entity.customer.CustomerAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {

    CustomerAddress toEntity(CustomerAddressDto customerAddressDto);

    @Mapping(target = "customerId", source = "customer.id")
    CustomerAddressDto toDto(CustomerAddress customerAddress);
}
