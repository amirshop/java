package com.ecommerce.backend.mapper.account;

import com.ecommerce.backend.dto.account.PermissionDto;
import com.ecommerce.backend.entity.account.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toEntity(PermissionDto permissionDto);
    PermissionDto toDto(Permission permission);
}
