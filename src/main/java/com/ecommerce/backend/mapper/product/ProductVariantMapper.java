package com.ecommerce.backend.mapper.product;

import com.ecommerce.backend.dto.product.ProductVariantDto;
import com.ecommerce.backend.entity.product.ProductVariant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    ProductVariant toEntity(ProductVariantDto dto);

    ProductVariantDto toDto(ProductVariant variant);
}
