package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.account.ShopSettingDto;
import com.ecommerce.backend.service.account.ShopSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class ShopSettingController {

    private final ShopSettingService shopSettingService;

    @GetMapping("/{accountId}")
    public ResponseEntity<ShopSettingDto> getShopSetting(@PathVariable UUID accountId) {
        return ResponseEntity.ok(shopSettingService.getShopSettingByAccountId(accountId));
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<ShopSettingDto> createShopSetting(@PathVariable UUID accountId,
                                                                     @RequestBody ShopSettingDto request) {
        return ResponseEntity.ok(shopSettingService.createShopSetting(accountId, request));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<ShopSettingDto> updateShopSetting(@PathVariable UUID accountId,
                                                                     @RequestBody ShopSettingDto request) {
        return ResponseEntity.ok(shopSettingService.updateShopSetting(accountId, request));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteShopSetting(@PathVariable UUID accountId) {
        shopSettingService.deleteShopSetting(accountId);
        return ResponseEntity.noContent().build();
    }
}

