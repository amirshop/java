package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.BrandDto;
import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.dto.product.TagDto;
import com.ecommerce.backend.entity.product.Brand;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.Tag;
import com.ecommerce.backend.repository.product.TagRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService extends BaseService<Tag, TagDto> {

    private final TagRepository tagRepository;

    private final ModelMapper modelMapper;

    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    public TagDto getTagById(UUID tagId) {
        return tagRepository.findById(tagId)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElse(null);
    }

    public TagDto createTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        Tag savedTag = tagRepository.save(tag);
        return modelMapper.map(savedTag, TagDto.class);
    }

    public void deleteTag(UUID tagId) {
        tagRepository.deleteById(tagId);
    }

    @Override
    protected Specification<Tag> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchTags(SearchDto requestDto) {
        return search(requestDto, TagDto.class);
    }
}
