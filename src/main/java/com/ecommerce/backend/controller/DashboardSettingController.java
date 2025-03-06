package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.account.DashboardSettingDto;
import com.ecommerce.backend.service.account.DashboardSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class DashboardSettingController {

    private final DashboardSettingService settingService;

    @GetMapping("/{accountId}")
    public ResponseEntity<DashboardSettingDto> getSetting(@PathVariable UUID accountId) {
        return ResponseEntity.ok(settingService.getSettingByAccountId(accountId));
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<DashboardSettingDto> createSetting(@PathVariable UUID accountId,
                                                                     @RequestBody DashboardSettingDto request) {
        return ResponseEntity.ok(settingService.createSetting(accountId, request));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<DashboardSettingDto> updateSetting(@PathVariable UUID accountId,
                                                                     @RequestBody DashboardSettingDto request) {
        return ResponseEntity.ok(settingService.updateSetting(accountId, request));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteSetting(@PathVariable UUID accountId) {
        settingService.deleteSetting(accountId);
        return ResponseEntity.noContent().build();
    }
}

