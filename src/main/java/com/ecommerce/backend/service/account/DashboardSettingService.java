package com.ecommerce.backend.service.account;

import com.ecommerce.backend.dto.account.request.DashboardSettingRequestDto;
import com.ecommerce.backend.dto.account.response.DashboardSettingResponseDto;
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

    public DashboardSettingResponseDto getSettingByAccountId(UUID accountId) {
        return settingRepository.findByAccountId(accountId)
                .map(setting -> modelMapper.map(setting, DashboardSettingResponseDto.class))
                .orElse(null);
    }

    public DashboardSettingResponseDto createSetting(UUID accountId, DashboardSettingRequestDto settingRequest) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return null; // Or throw an exception
        }
        DashboardSetting setting = modelMapper.map(settingRequest, DashboardSetting.class);
        setting.setAccount(accountOpt.get());
        DashboardSetting savedSetting = settingRepository.save(setting);
        return modelMapper.map(savedSetting, DashboardSettingResponseDto.class);
    }

    public DashboardSettingResponseDto updateSetting(UUID accountId, DashboardSettingRequestDto settingRequest) {
        return settingRepository.findByAccountId(accountId)
                .map(existing -> {
                    modelMapper.map(settingRequest, existing);
                    DashboardSetting updated = settingRepository.save(existing);
                    return modelMapper.map(updated, DashboardSettingResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteSetting(UUID accountId) {
        settingRepository.findByAccountId(accountId).ifPresent(settingRepository::delete);
    }
}

