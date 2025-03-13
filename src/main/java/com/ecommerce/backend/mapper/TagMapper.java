package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.product.TagDto;
import com.ecommerce.backend.entity.product.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(TagDto tagDto);
    TagDto toDto(Tag tag);
}
