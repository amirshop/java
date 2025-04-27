package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.product.ProductVariantAttributeDto;
import com.ecommerce.backend.entity.product.ProductVariantAttribute;
import com.ecommerce.backend.mapper.product.ProductVariantAttributeMapper;
import com.ecommerce.backend.repository.product.ProductVariantAttributeRepository;
import com.ecommerce.backend.service.BaseService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductVariantAttributeService extends BaseService<ProductVariantAttribute, ProductVariantAttributeDto> {

    private final ProductVariantAttributeRepository attributeRepository;

    private final ProductVariantAttributeMapper productVariantAttributeMapper;

    public ProductVariantAttributeService(ProductVariantAttributeRepository attributeRepository,
                                          ProductVariantAttributeMapper productVariantAttributeMapper) {
        super(attributeRepository, productVariantAttributeMapper::toDto);
        this.attributeRepository = attributeRepository;
        this.productVariantAttributeMapper = productVariantAttributeMapper;
    }

    public ProductVariantAttributeDto saveAttribute(ProductVariantAttributeDto attributeDTO) {
        ProductVariantAttribute attribute = productVariantAttributeMapper.toEntity(attributeDTO);
        return productVariantAttributeMapper.toDto(attributeRepository.save(attribute));
    }

    public Optional<ProductVariantAttributeDto> getAttributeById(UUID attributeId) {
        Optional<ProductVariantAttribute> attribute = attributeRepository.findById(attributeId);
        return attribute.map(productVariantAttributeMapper::toDto);
    }

    public List<ProductVariantAttributeDto> getAttributesByVariantId(UUID variantId) {
        return attributeRepository.findAllByVariantId(variantId).stream()
                .map(productVariantAttributeMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteAttribute(UUID attributeId) {
        attributeRepository.deleteById(attributeId);
    }

    @Override
    protected Specification<ProductVariantAttribute> createSpecification(FilterCriteria filter) {
        return null;
    }
}

