package com.ecommerce.backend.mapper.product;

import com.ecommerce.backend.dto.product.AttributeDto;
import com.ecommerce.backend.dto.product.TagDto;
import com.ecommerce.backend.entity.product.Attribute;
import com.ecommerce.backend.entity.product.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttributeMapper {

    Attribute toEntity(AttributeDto attributeDto);
    AttributeDto toDto(Attribute attribute);
}
