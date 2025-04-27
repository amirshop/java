package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.ProductVariantDto;
import com.ecommerce.backend.entity.product.ProductVariant;
import com.ecommerce.backend.mapper.product.ProductVariantMapper;
import com.ecommerce.backend.repository.product.ProductVariantRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductVariantService extends BaseService<ProductVariant, ProductVariantDto> {

    private final ProductVariantRepository productVariantRepository;

    private final ProductVariantMapper productVariantMapper;

    public ProductVariantService(ProductVariantRepository productVariantRepository,
                                 ProductVariantMapper productVariantMapper) {
        super(productVariantRepository, productVariantMapper::toDto);
        this.productVariantRepository = productVariantRepository;
        this.productVariantMapper = productVariantMapper;
    }

    public ProductVariantDto saveVariant(ProductVariantDto ProductVariantDto) {
        ProductVariant productVariant = productVariantMapper.toEntity(ProductVariantDto);
        return productVariantMapper.toDto(productVariantRepository.save(productVariant));
    }

    public Optional<ProductVariantDto> getVariantById(UUID variantId) {
        Optional<ProductVariant> productVariant = productVariantRepository.findById(variantId);
        return productVariant.map(productVariantMapper::toDto);
    }

    public List<ProductVariantDto> getVariantsByProductId(UUID productId) {
        return productVariantRepository.findAllByProductId(productId).stream()
                .map(productVariantMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteVariant(UUID variantId) {
        productVariantRepository.deleteById(variantId);
    }

    @Override
    protected Specification<ProductVariant> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchProducts(SearchDto requestDto) {
        return search(requestDto, ProductVariantDto.class);
    }
}

