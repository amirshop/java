package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.account.request.DashboardSettingRequestDto;
import com.ecommerce.backend.dto.account.response.DashboardSettingResponseDto;
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
    public ResponseEntity<DashboardSettingResponseDto> getSetting(@PathVariable UUID accountId) {
        return ResponseEntity.ok(settingService.getSettingByAccountId(accountId));
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<DashboardSettingResponseDto> createSetting(@PathVariable UUID accountId,
                                                                     @RequestBody DashboardSettingRequestDto request) {
        return ResponseEntity.ok(settingService.createSetting(accountId, request));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<DashboardSettingResponseDto> updateSetting(@PathVariable UUID accountId,
                                                                     @RequestBody DashboardSettingRequestDto request) {
        return ResponseEntity.ok(settingService.updateSetting(accountId, request));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteSetting(@PathVariable UUID accountId) {
        settingService.deleteSetting(accountId);
        return ResponseEntity.noContent().build();
    }
}

