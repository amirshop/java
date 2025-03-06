package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.account.DashboardSettingDto;
import com.ecommerce.backend.entity.account.Account;
import com.ecommerce.backend.entity.account.DashboardSetting;
import com.ecommerce.backend.repository.account.AccountRepository;
import com.ecommerce.backend.repository.account.DashboardSettingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardSettingService {

    private final DashboardSettingRepository settingRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public DashboardSettingDto getSettingByAccountId(UUID accountId) {
        return settingRepository.findByAccountId(accountId)
                .map(setting -> modelMapper.map(setting, DashboardSettingDto.class))
                .orElse(null);
    }

    public DashboardSettingDto createSetting(UUID accountId, DashboardSettingDto settingRequest) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return null; // Or throw an exception
        }
        DashboardSetting setting = modelMapper.map(settingRequest, DashboardSetting.class);
        setting.setAccount(accountOpt.get());
        DashboardSetting savedSetting = settingRepository.save(setting);
        return modelMapper.map(savedSetting, DashboardSettingDto.class);
    }

    public DashboardSettingDto updateSetting(UUID accountId, DashboardSettingDto settingRequest) {
        return settingRepository.findByAccountId(accountId)
                .map(existing -> {
                    modelMapper.map(settingRequest, existing);
                    DashboardSetting updated = settingRepository.save(existing);
                    return modelMapper.map(updated, DashboardSettingDto.class);
                })
                .orElse(null);
    }

    public void deleteSetting(UUID accountId) {
        settingRepository.findByAccountId(accountId).ifPresent(settingRepository::delete);
    }
}

