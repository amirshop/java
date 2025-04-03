package com.ecommerce.backend.mapper.product;

import com.ecommerce.backend.dto.product.ProductVariantDTO;
import com.ecommerce.backend.entity.product.ProductVariant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    ProductVariant toEntity(ProductVariantDTO dto);

    ProductVariantDTO toDto(ProductVariant variant);
}
