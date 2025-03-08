package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.entity.product.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

//    @Mapping(source = "category.name", target = "categoryName")
//    @Mapping(source = "brand.name", target = "brandName")
    ProductDto toDto(Product product);
}

