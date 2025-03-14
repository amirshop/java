package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.account.ShopSettingDto;
import com.ecommerce.backend.entity.account.Account;
import com.ecommerce.backend.entity.account.ShopSetting;
import com.ecommerce.backend.repository.account.AccountRepository;
import com.ecommerce.backend.repository.account.ShopSettingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopSettingService {

    private final ShopSettingRepository shopSettingRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public ShopSettingDto getShopSettingByAccountId(UUID accountId) {
        return shopSettingRepository.findByAccountId(accountId)
                .map(setting -> modelMapper.map(setting, ShopSettingDto.class))
                .orElse(null);
    }

    public ShopSettingDto createShopSetting(UUID accountId, ShopSettingDto settingRequest) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return null; // Or throw an exception
        }
        ShopSetting shopSetting = modelMapper.map(settingRequest, ShopSetting.class);
        shopSetting.setAccount(accountOpt.get());
        ShopSetting savedSetting = shopSettingRepository.save(shopSetting);
        return modelMapper.map(savedSetting, ShopSettingDto.class);
    }

    public ShopSettingDto updateShopSetting(UUID accountId, ShopSettingDto settingRequest) {
        return shopSettingRepository.findByAccountId(accountId)
                .map(existing -> {
                    modelMapper.map(settingRequest, existing);
                    ShopSetting updated = shopSettingRepository.save(existing);
                    return modelMapper.map(updated, ShopSettingDto.class);
                })
                .orElse(null);
    }

    public void deleteShopSetting(UUID accountId) {
        shopSettingRepository.findByAccountId(accountId).ifPresent(shopSettingRepository::delete);
    }
}

