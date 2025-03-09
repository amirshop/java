package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.RoleDto;
import com.ecommerce.backend.entity.account.Role;
import com.ecommerce.backend.repository.account.RoleRepository;
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
public class RoleService extends BaseService<Role, RoleDto> {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    public RoleDto getRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .map(role -> modelMapper.map(role, RoleDto.class))
                .orElse(null);
    }

    public RoleDto createRole(RoleDto roleRequest) {
        Role role = modelMapper.map(roleRequest, Role.class);
        Role saved = roleRepository.save(role);
        return modelMapper.map(saved, RoleDto.class);
    }

    public RoleDto updateRole(UUID roleId, RoleDto roleRequest) {
        return roleRepository.findById(roleId)
                .map(existing -> {
                    existing.setValue(roleRequest.getValue());
                    // Optionally update associated permissions if needed.
                    Role updated = roleRepository.save(existing);
                    return modelMapper.map(updated, RoleDto.class);
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

