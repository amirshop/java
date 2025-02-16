package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.account.request.RoleRequestDto;
import com.ecommerce.backend.dto.account.response.RoleResponseDto;
import com.ecommerce.backend.entity.account.Role;
import com.ecommerce.backend.repository.account.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public List<RoleResponseDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleResponseDto.class))
                .collect(Collectors.toList());
    }

    public RoleResponseDto getRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .map(role -> modelMapper.map(role, RoleResponseDto.class))
                .orElse(null);
    }

    public RoleResponseDto createRole(RoleRequestDto roleRequest) {
        Role role = modelMapper.map(roleRequest, Role.class);
        Role saved = roleRepository.save(role);
        return modelMapper.map(saved, RoleResponseDto.class);
    }

    public RoleResponseDto updateRole(UUID roleId, RoleRequestDto roleRequest) {
        return roleRepository.findById(roleId)
                .map(existing -> {
                    existing.setName(roleRequest.getName());
                    // Optionally update associated permissions if needed.
                    Role updated = roleRepository.save(existing);
                    return modelMapper.map(updated, RoleResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteRole(UUID roleId) {
        roleRepository.deleteById(roleId);
    }
}

