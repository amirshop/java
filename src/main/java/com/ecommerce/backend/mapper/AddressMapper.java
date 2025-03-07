package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.account.AddressDto;
import com.ecommerce.backend.entity.account.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDto addressDto);
    AddressDto toDto(Address address);
}