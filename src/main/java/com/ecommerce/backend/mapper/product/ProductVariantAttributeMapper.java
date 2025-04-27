package com.ecommerce.backend.mapper.product;

import com.ecommerce.backend.dto.product.ProductVariantAttributeDto;
import com.ecommerce.backend.entity.product.ProductVariantAttribute;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVariantAttributeMapper {

    ProductVariantAttribute toEntity(ProductVariantAttributeDto dto);

    ProductVariantAttributeDto toDto(ProductVariantAttribute attribute);
}
