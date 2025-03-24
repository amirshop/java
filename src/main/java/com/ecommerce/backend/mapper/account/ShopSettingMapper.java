package com.ecommerce.backend.mapper.account;

import com.ecommerce.backend.dto.account.ShopSettingDto;
import com.ecommerce.backend.entity.account.ShopSetting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopSettingMapper {

    ShopSetting toEntity(ShopSettingDto shopSettingDto);

    @Mapping(target = "accountId", source = "userAccount.id")
    ShopSettingDto toDto(ShopSetting shopSetting);
}
