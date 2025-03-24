package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.account.ShopSettingDto;
import com.ecommerce.backend.entity.account.ShopSetting;
import com.ecommerce.backend.entity.account.UserAccount;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.account.ShopSettingMapper;
import com.ecommerce.backend.repository.account.ShopSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopSettingService {

    private final ShopSettingRepository shopSettingRepository;
    private final UserAccountService userAccountService;
    private final ShopSettingMapper shopSettingMapper;

    public ShopSettingDto getShopSettingByAccountId(UUID accountId) {
        return shopSettingRepository.findByUserAccountId(accountId)
                .map(shopSettingMapper::toDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("UserAccount", "id", accountId.toString()));
    }

    public ShopSettingDto getShopSettingById(UUID shopId) {
        return shopSettingRepository.findById(shopId).map(shopSettingMapper::toDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("ShopSetting", "id", shopId.toString()));
    }

    public ShopSettingDto createShopSetting(UUID accountId, ShopSettingDto settingRequest) {
        UserAccount accountOpt = userAccountService.findById(accountId);
        ShopSetting shopSetting = shopSettingMapper.toEntity(settingRequest);
        shopSetting.setUserAccount(accountOpt);
        shopSetting.setCreatedAt(new Date());
        shopSetting.setUpdatedAt(new Date());
        ShopSetting savedSetting = shopSettingRepository.save(shopSetting);
        return shopSettingMapper.toDto(savedSetting);
    }

    public ShopSettingDto updateShopSetting(UUID accountId, ShopSettingDto settingRequest) {
        return shopSettingRepository.findByUserAccountId(accountId)
                .map(shopSetting -> {
                    Optional.ofNullable(settingRequest.getColor())
                            .filter(color -> !color.isBlank())
                            .ifPresent(shopSetting::setColor);
                    Optional.ofNullable(settingRequest.getFavicon())
                            .filter(favicon -> !favicon.isBlank())
                            .ifPresent(shopSetting::setFavicon);
                    Optional.ofNullable(settingRequest.getDescription())
                            .filter(description -> !description.isBlank())
                            .ifPresent(shopSetting::setDescription);
                    Optional.ofNullable(settingRequest.getCurrency())
                            .filter(currency -> !currency.isBlank())
                            .ifPresent(shopSetting::setCurrency);
                    Optional.ofNullable(settingRequest.getLogo())
                            .filter(logo -> !logo.isBlank())
                            .ifPresent(shopSetting::setLogo);
                    Optional.ofNullable(settingRequest.getSlug())
                            .filter(slug -> !slug.isBlank())
                            .ifPresent(shopSetting::setSlug);
                    Optional.ofNullable(settingRequest.getTitle())
                            .filter(title -> !title.isBlank())
                            .ifPresent(shopSetting::setTitle);
                    shopSetting.setUpdatedAt(new Date());
                    ShopSetting updated = shopSettingRepository.save(shopSetting);
                    return shopSettingMapper.toDto(updated);
                })
                .orElseThrow(() -> new ResourceNotFoundException("UserAccount", "id", accountId.toString()
                ));
    }

    public void deleteShopSetting(UUID accountId) {
        shopSettingRepository.findByUserAccountId(accountId).ifPresent(shopSettingRepository::delete);
    }
}

