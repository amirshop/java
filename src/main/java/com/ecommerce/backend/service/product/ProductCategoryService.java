package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.ProductCategoryDto;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.mapper.product.ProductCategoryMapper;
import com.ecommerce.backend.repository.product.ProductCategoryRepository;
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
public class ProductCategoryService extends BaseService<ProductCategory, ProductCategoryDto> {

    private final ProductCategoryRepository categoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    public ProductCategoryService(ProductCategoryRepository categoryRepository,
                                  ProductCategoryMapper productCategoryMapper) {
        super(categoryRepository, productCategoryMapper::toDto);
        this.categoryRepository = categoryRepository;
        this.productCategoryMapper = productCategoryMapper;
    }

    public List<ProductCategoryDto> getAllCategories() {
        List<ProductCategory> categories = categoryRepository.findAll();
        return categories.stream()
                .map(productCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductCategoryDto getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(productCategoryMapper::toDto)
                .orElse(null);
    }

    public ProductCategoryDto createCategory(ProductCategoryDto categoryRequest) {
        checkExistSlug(categoryRequest.getSlug());
        ProductCategory category = productCategoryMapper.toEntity(categoryRequest);
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        ProductCategory saved = categoryRepository.save(category);
        return productCategoryMapper.toDto(saved);
    }

    private void checkExistSlug(String slug) {
        if (categoryRepository.existsBySlug(slug)) {
            throw new ResourceAlreadyExistsException("slug in productCategory already exists");
        }
    }

    public ProductCategoryDto updateCategory(UUID categoryId, ProductCategoryDto categoryRequest) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    Optional.ofNullable(categoryRequest.getName())
                            .filter(name -> !name.isBlank())
                            .ifPresent(category::setName);
                    Optional.ofNullable(categoryRequest.getDescription())
                            .filter(description -> !description.isBlank())
                            .ifPresent(category::setDescription);
                    Optional.ofNullable(categoryRequest.getSlug())
                            .filter(slug -> !slug.isBlank())
                            .filter(this::isSlugUnique)
                            .ifPresent(category::setSlug);
                    ProductCategory updatedCategory = categoryRepository.save(category);
                    return productCategoryMapper.toDto(updatedCategory);
                })
                .orElse(null);
    }

    private boolean isSlugUnique(String slug) {
        return !categoryRepository.existsBySlug(slug);
    }

    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    protected Specification<ProductCategory> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchProductCategories(SearchDto requestDto) {
        return search(requestDto, ProductCategoryDto.class);
    }
}
