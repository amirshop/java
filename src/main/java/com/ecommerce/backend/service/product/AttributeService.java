package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.AttributeDto;
import com.ecommerce.backend.entity.product.Attribute;
import com.ecommerce.backend.mapper.product.AttributeMapper;
import com.ecommerce.backend.repository.product.AttributeRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttributeService extends BaseService<Attribute, AttributeDto> {

    private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;

    public AttributeService(AttributeRepository attributeRepository, AttributeMapper attributeMapper) {
        super(attributeRepository, attributeMapper::toDto);
        this.attributeRepository = attributeRepository;
        this.attributeMapper = attributeMapper;
    }

    public List<AttributeDto> getAllAttributes() {
        return attributeRepository.findAll()
                .stream()
                .map(attributeMapper::toDto)
                .collect(Collectors.toList());
    }

    public AttributeDto getAttributeById(UUID attributeId) {
        return attributeRepository.findById(attributeId)
                .map(attributeMapper::toDto)
                .orElse(null);
    }

    public AttributeDto createAttribute(AttributeDto attributeDto) {
        Attribute attribute = attributeMapper.toEntity(attributeDto);
        attribute.setCreatedAt(new Date());
        attribute.setUpdatedAt(new Date());
        Attribute savedAttribute = attributeRepository.save(attribute);
        return attributeMapper.toDto(savedAttribute);
    }

    public AttributeDto updateAttribute(UUID attributeId, AttributeDto attributeDto) {
        return attributeRepository.findById(attributeId)
                .map(attribute-> {
                    Optional.ofNullable(attributeDto.getLabel())
                            .filter(label -> !label.isBlank())
                            .ifPresent(attribute::setLabel);
                    attribute.setUpdatedAt(new Date());
                    Attribute updatedAttribute = attributeRepository.save(attribute);
                    return attributeMapper.toDto(updatedAttribute);
                })
                .orElse(null);
    }

    public void deleteAttribute(UUID tagId) {
        attributeRepository.deleteById(tagId);
    }

    @Override
    protected Specification<Attribute> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchAttributes(SearchDto requestDto) {
        return search(requestDto, AttributeDto.class);
    }

}
