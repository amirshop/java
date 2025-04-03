package com.ecommerce.backend.mapper.product;

import com.ecommerce.backend.dto.product.ProductVariantAttributeDTO;
import com.ecommerce.backend.entity.product.ProductVariantAttribute;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVariantAttributeMapper {

    ProductVariantAttribute toEntity(ProductVariantAttributeDTO dto);

    ProductVariantAttributeDTO toDto(ProductVariantAttribute attribute);
}
