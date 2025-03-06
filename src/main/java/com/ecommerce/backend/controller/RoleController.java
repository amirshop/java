package com.ecommerce.backend.controller;

import java.util.List;
import java.util.UUID;

import com.ecommerce.backend.dto.account.RoleDto;
import com.ecommerce.backend.service.account.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable UUID roleId) {
        RoleDto role = roleService.getRoleById(roleId);
        return role != null
                ? ResponseEntity.ok(role)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleRequest) {
        RoleDto createdRole = roleService.createRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable UUID roleId, @RequestBody RoleDto roleRequest) {
        RoleDto updatedRole = roleService.updateRole(roleId, roleRequest);
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

