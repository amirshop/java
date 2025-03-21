package com.ecommerce.backend.mapper.account;

import com.ecommerce.backend.dto.account.UserProfileDto;
import com.ecommerce.backend.entity.account.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface UserProfileMapper {

    @Mapping(target = "address", source = "address")
    UserProfile toEntity(UserProfileDto userProfileDto);

    @Mapping(target = "address", source = "address")
    UserProfileDto toDto(UserProfile userProfile);
}

