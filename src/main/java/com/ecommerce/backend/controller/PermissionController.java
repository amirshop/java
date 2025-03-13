package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.PermissionDto;
import com.ecommerce.backend.service.account.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<PermissionDto>> getAllPermissions() {
        List<PermissionDto> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionDto> getPermissionById(@PathVariable UUID permissionId) {
        PermissionDto permission = permissionService.getPermissionById(permissionId);
        return permission != null
                ? ResponseEntity.ok(permission)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto permissionRequest) {
        PermissionDto createdPermission = permissionService.createPermission(permissionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPermission);
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionDto> updatePermission(@PathVariable UUID permissionId,
                                                                  @RequestBody PermissionDto permissionRequest) {
        PermissionDto updatedPermission = permissionService.updatePermission(permissionId, permissionRequest);
        return updatedPermission != null
                ? ResponseEntity.ok(updatedPermission)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deletePermission(@PathVariable UUID permissionId) {
        permissionService.deletePermission(permissionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseDto searchBrands(@RequestBody SearchDto requestDto) {
        return permissionService.searchPermissions(requestDto);
    }
}

