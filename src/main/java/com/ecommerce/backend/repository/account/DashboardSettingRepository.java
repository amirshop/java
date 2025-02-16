package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.DashboardSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DashboardSettingRepository  extends JpaRepository<DashboardSetting, UUID> {
    Optional<DashboardSetting> findByAccountId(UUID accountId);
}
