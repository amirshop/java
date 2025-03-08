package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.product.BrandDto;
import com.ecommerce.backend.entity.product.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandDto toDto(Brand brand);
}
