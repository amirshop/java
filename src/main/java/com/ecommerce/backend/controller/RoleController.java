package com.ecommerce.backend.controller;

import java.util.List;
import java.util.UUID;

import com.ecommerce.backend.dto.account.request.RoleRequestDto;
import com.ecommerce.backend.dto.account.response.RoleResponseDto;
import com.ecommerce.backend.service.account.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<RoleResponseDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable UUID roleId) {
        RoleResponseDto role = roleService.getRoleById(roleId);
        return role != null
                ? ResponseEntity.ok(role)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleRequestDto roleRequest) {
        RoleResponseDto createdRole = roleService.createRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable UUID roleId, @RequestBody RoleRequestDto roleRequest) {
        RoleResponseDto updatedRole = roleService.updateRole(roleId, roleRequest);
        return updatedRole != null
                ? ResponseEntity.ok(updatedRole)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}

