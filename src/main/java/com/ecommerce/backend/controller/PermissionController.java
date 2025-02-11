package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.account.request.PermissionRequestDto;
import com.ecommerce.backend.dto.account.response.PermissionResponseDto;
import com.ecommerce.backend.service.account.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<PermissionResponseDto>> getAllPermissions() {
        List<PermissionResponseDto> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionResponseDto> getPermissionById(@PathVariable Long permissionId) {
        PermissionResponseDto permission = permissionService.getPermissionById(permissionId);
        return permission != null
                ? ResponseEntity.ok(permission)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PermissionResponseDto> createPermission(@RequestBody PermissionRequestDto permissionRequest) {
        PermissionResponseDto createdPermission = permissionService.createPermission(permissionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPermission);
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionResponseDto> updatePermission(@PathVariable Long permissionId,
                                                                  @RequestBody PermissionRequestDto permissionRequest) {
        PermissionResponseDto updatedPermission = permissionService.updatePermission(permissionId, permissionRequest);
        return updatedPermission != null
                ? ResponseEntity.ok(updatedPermission)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long permissionId) {
        permissionService.deletePermission(permissionId);
        return ResponseEntity.noContent().build();
    }
}

