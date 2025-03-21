package com.ecommerce.backend.mapper.account;

import com.ecommerce.backend.dto.account.RoleDto;
import com.ecommerce.backend.entity.account.Permission;
import com.ecommerce.backend.entity.account.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapPermissionsToUUIDs")
    RoleDto toDto(Role role);

    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapUUIDsToPermissions")
    Role toEntity(RoleDto roleDto);

    @Named("mapPermissionsToUUIDs")
    default Set<UUID> mapPermissionsToUUIDs(Set<Permission> permissions) {
        return permissions != null
                ? permissions.stream().map(Permission::getId).collect(Collectors.toSet())
                : new HashSet<>();
    }

    @Named("mapUUIDsToPermissions")
    default Set<Permission> mapUUIDsToPermissions(Set<UUID> permissionIds) {
        return permissionIds != null
                ? permissionIds.stream()
                .map(id -> {
                    Permission permission = new Permission();
                    permission.setId(id);
                    return permission;
                })
                .collect(Collectors.toSet())
                : new HashSet<>();
    }
}

