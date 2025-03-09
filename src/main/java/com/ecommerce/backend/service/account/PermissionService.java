package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.account.PermissionDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.entity.account.Permission;
import com.ecommerce.backend.repository.account.PermissionRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService extends BaseService<Permission, PermissionDto> {

    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    public List<PermissionDto> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permission -> modelMapper.map(permission, PermissionDto.class))
                .collect(Collectors.toList());
    }

    public PermissionDto getPermissionById(UUID permissionId) {
        return permissionRepository.findById(permissionId)
                .map(permission -> modelMapper.map(permission, PermissionDto.class))
                .orElse(null);
    }

    public PermissionDto createPermission(PermissionDto permissionRequest) {
        Permission permission = modelMapper.map(permissionRequest, Permission.class);
        Permission saved = permissionRepository.save(permission);
        return modelMapper.map(saved, PermissionDto.class);
    }

    public PermissionDto updatePermission(UUID permissionId, PermissionDto permissionRequest) {
        return permissionRepository.findById(permissionId)
                .map(existing -> {
                    existing.setValue(permissionRequest.getValue());
                    Permission updated = permissionRepository.save(existing);
                    return modelMapper.map(updated, PermissionDto.class);
                })
                .orElse(null);
    }

    public void deletePermission(UUID permissionId) {
        permissionRepository.deleteById(permissionId);
    }

    @Override
    protected Specification<Permission> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    // Expose a method that calls the generic search functionality
    public ResponseDto searchPermissions(SearchDto requestDto) {
        return search(requestDto, PermissionDto.class);
    }
}

