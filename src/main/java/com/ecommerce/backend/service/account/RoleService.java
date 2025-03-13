package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.PermissionDto;
import com.ecommerce.backend.dto.account.RoleDto;
import com.ecommerce.backend.entity.account.Permission;
import com.ecommerce.backend.entity.account.Role;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.RoleMapper;
import com.ecommerce.backend.repository.account.RoleRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseService<Role, RoleDto> {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;

    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper, RoleMapper roleMapper,
                       PermissionService permissionService) {
        super(roleRepository, role -> modelMapper.map(role, RoleDto.class));
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.roleMapper = roleMapper;
        this.permissionService = permissionService;
    }

    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleDto getRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .map(roleMapper::toDto)
                .orElse(null);
    }

    public RoleDto createRole(RoleDto roleRequest) {

        checkExistValue(roleRequest.getValue());

        // Convert DTO to entity
        Role role = roleMapper.toEntity(roleRequest);

        // Check and set permissions
        if (roleRequest.getPermissions() != null && !roleRequest.getPermissions().isEmpty()) {
            Set<Permission> permissions = roleRequest.getPermissions().stream()
                    .map(permissionService::findById)
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }

        role.setCreatedAt(new Date());
        role.setUpdatedAt(new Date());

        // Save role with permissions
        Role savedRole = roleRepository.save(role);

        // Convert entity back to DTO and return
        return roleMapper.toDto(savedRole);

    }

    private void checkExistValue(String value) {
        if (roleRepository.existsByValue(value)) {
            throw new ResourceAlreadyExistsException("value already exists.");
        }
    }

    public RoleDto updateRole(UUID roleId, RoleDto roleRequest) {
        return roleRepository.findById(roleId)
                .map(existing -> {
                    existing.setValue(roleRequest.getValue());
                    existing.setLabel(roleRequest.getLabel());
                    existing.setUpdatedAt(new Date());
                    // Optionally update associated permissions if needed.
                    Role updated = roleRepository.save(existing);
                    return roleMapper.toDto(updated);
                })
                .orElse(null);
    }

    public void deleteRole(UUID roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    protected Specification<Role> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchRoles(SearchDto requestDto) {
        return search(requestDto, RoleDto.class);
    }
}

