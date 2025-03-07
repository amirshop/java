package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.account.RoleDto;
import com.ecommerce.backend.entity.account.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto roleDto);
    RoleDto toDto(Role role);
}
