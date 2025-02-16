package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.account.request.PermissionRequestDto;
import com.ecommerce.backend.dto.account.response.PermissionResponseDto;
import com.ecommerce.backend.entity.account.Permission;
import com.ecommerce.backend.repository.account.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    public List<PermissionResponseDto> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permission -> modelMapper.map(permission, PermissionResponseDto.class))
                .collect(Collectors.toList());
    }

    public PermissionResponseDto getPermissionById(UUID permissionId) {
        return permissionRepository.findById(permissionId)
                .map(permission -> modelMapper.map(permission, PermissionResponseDto.class))
                .orElse(null);
    }

    public PermissionResponseDto createPermission(PermissionRequestDto permissionRequest) {
        Permission permission = modelMapper.map(permissionRequest, Permission.class);
        Permission saved = permissionRepository.save(permission);
        return modelMapper.map(saved, PermissionResponseDto.class);
    }

    public PermissionResponseDto updatePermission(UUID permissionId, PermissionRequestDto permissionRequest) {
        return permissionRepository.findById(permissionId)
                .map(existing -> {
                    existing.setValue(permissionRequest.getValue());
                    Permission updated = permissionRepository.save(existing);
                    return modelMapper.map(updated, PermissionResponseDto.class);
                })
                .orElse(null);
    }

    public void deletePermission(UUID permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}

