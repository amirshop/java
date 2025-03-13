package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.account.AddressDto;
import com.ecommerce.backend.dto.account.PermissionDto;
import com.ecommerce.backend.entity.account.Address;
import com.ecommerce.backend.entity.account.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toEntity(PermissionDto permissionDto);
    PermissionDto toDto(Permission permission);
}
