package com.ecommerce.backend.mapper.customer;

import com.ecommerce.backend.dto.customer.CustomerAddressDto;
import com.ecommerce.backend.entity.customer.CustomerAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {

    CustomerAddress toEntity(CustomerAddressDto customerAddressDto);
    CustomerAddressDto toDto(CustomerAddress customerAddress);
}
