package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.ShopSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShopSettingRepository  extends JpaRepository<ShopSetting, UUID> {
    Optional<ShopSetting> findByUserAccountId(UUID accountId);
}
