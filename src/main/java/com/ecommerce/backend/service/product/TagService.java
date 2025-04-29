package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.BrandDto;
import com.ecommerce.backend.dto.product.TagDto;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.entity.product.Tag;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.product.TagMapper;
import com.ecommerce.backend.repository.product.TagRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TagService extends BaseService<Tag, TagDto> {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        super(tagRepository, tagMapper::toDto);
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    public TagDto getTagById(UUID tagId) {
        return tagRepository.findById(tagId)
                .map(tagMapper::toDto)
                .orElse(null);
    }

    public Tag findById(UUID tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("tag", "id", tagId.toString()));
    }

    public TagDto createTag(TagDto tagDto) {
        Tag tag = tagMapper.toEntity(tagDto);
        tag.setCreatedAt(new Date());
        tag.setUpdatedAt(new Date());
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toDto(savedTag);
    }

    public TagDto updateTag(UUID tagId, TagDto tagDto) {
        return tagRepository.findById(tagId)
                .map(tag -> {
                    Optional.ofNullable(tagDto.getName())
                            .filter(name -> !name.isBlank())
                            .ifPresent(tag::setName);
                    Optional.ofNullable(tagDto.getDescription())
                            .filter(description -> !description.isBlank())
                            .ifPresent(tag::setDescription);
                    Optional.ofNullable(tagDto.getSlug())
                            .filter(slug -> !slug.isBlank())
                            .filter(this::isSlugUnique)
                            .ifPresent(tag::setSlug);
                    tag.setUpdatedAt(new Date());
                    Tag updatedTag = tagRepository.save(tag);
                    return tagMapper.toDto(updatedTag);
                })
                .orElse(null);
    }

    private boolean isSlugUnique(String slug) {
        return !tagRepository.existsBySlug(slug);
    }

    public void deleteTag(UUID tagId) {
        tagRepository.deleteById(tagId);
    }

    public Tag findByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("tag", "name", name));
    }

    @Override
    protected Specification<Tag> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchTags(SearchDto requestDto) {
        return search(requestDto, BrandDto.class);
    }

}
