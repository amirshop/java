package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.product.ProductCategoryDto;
import com.ecommerce.backend.entity.product.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory toEntity(ProductCategoryDto productCategoryDto);
    ProductCategoryDto toDto(ProductCategory productCategory);
}
