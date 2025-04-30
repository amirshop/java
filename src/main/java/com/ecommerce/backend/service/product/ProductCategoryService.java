package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.ProductCategoryDto;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.exception.ResourceAlreadyExistsException;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.mapper.product.ProductCategoryMapper;
import com.ecommerce.backend.repository.product.ProductCategoryRepository;
import com.ecommerce.backend.service.BaseService;
import com.ecommerce.backend.specification.GenericSpecification;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
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
        List<ProductCategory> categories = categoryRepository.findAllByParentIdIsNullOrderByPriorityAscCreatedAtAsc();
        return categories.stream()
                .map(productCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductCategoryDto getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(productCategoryMapper::toDto)
                .orElse(null);
    }

    public ProductCategory findCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category", "id", categoryId.toString())
        );
    }

    //TODO: test this method
    public ProductCategoryDto createCategory(ProductCategoryDto categoryRequest) {
        checkExistSlug(categoryRequest.getSlug());
        ProductCategory productCategory;
        if (categoryRequest.getParentId() == null) {
            checkExistSlug(categoryRequest.getSlug());
            productCategory = addNewParentCategory(categoryRequest);
        } else {
            productCategory = addNewChildCategory(categoryRequest);
        }
        return productCategoryMapper.toDto(productCategory);
    }

    private ProductCategory addNewParentCategory(ProductCategoryDto categoryRequest){
        ProductCategory productCategory = productCategoryMapper.toEntity(categoryRequest);
        productCategory.setCreatedAt(new Date());
        productCategory.setUpdatedAt(new Date());
        return categoryRepository.save(productCategory);
    }

    //TODO: check why return parentID null response?
    private ProductCategory addNewChildCategory(ProductCategoryDto categoryRequest){
        ProductCategory parentCategory = categoryRepository.findById(categoryRequest.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("productCategory", "parentId", categoryRequest.getParentId().toString()));
        ProductCategory productCategory = productCategoryMapper.toEntity(categoryRequest);
        productCategory.setParentId(categoryRequest.getParentId());
        productCategory.setSlug(getChildSlug(parentCategory, categoryRequest));
        productCategory.setPriority(parentCategory.getSubCategory().size() + 1);
        Set<ProductCategory> subProductCategory = parentCategory.getSubCategory();
        productCategory.setCreatedAt(new Date());
        productCategory.setUpdatedAt(new Date());
        subProductCategory.add(productCategory);
        parentCategory.setSubCategory(subProductCategory);
        ProductCategory savedParent = categoryRepository.save(parentCategory);
        return savedParent.getSubCategory().stream()
                .filter(c -> c.getSlug().equals(productCategory.getSlug()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Child not found"));
    }

    private String getChildSlug(ProductCategory parentCategory, ProductCategoryDto categoryReqDto) {
        String url = parentCategory.getSlug() + "/" + categoryReqDto.getSlug();
        checkExistSlug(url);
        return url;
    }

    private void checkExistSlug(String slug) {
        if (categoryRepository.existsBySlug(slug)) {
            throw new ResourceAlreadyExistsException("slug in productCategory already exists");
        }
    }

    //TODO: check update
    public ProductCategoryDto updateCategory(UUID categoryId, ProductCategoryDto categoryRequest) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    Optional.ofNullable(categoryRequest.getName())
                            .filter(name -> !name.isBlank())
                            .ifPresent(category::setName);
                    Optional.ofNullable(categoryRequest.getDescription())
                            .filter(description -> !description.isBlank())
                            .ifPresent(category::setDescription);
                    Optional.ofNullable(categoryRequest.getPriority())
                            .ifPresent(category::setPriority);

                    //TODO: can't change slug and is child or parent
//                    Optional.ofNullable(categoryRequest.getSlug())
//                            .filter(slug -> !slug.isBlank())
//                            .filter(this::isSlugUnique)
//                            .ifPresent(category::setSlug);

                    category.setUpdatedAt(new Date());

                    ProductCategory updatedCategory = categoryRepository.save(category);
                    return productCategoryMapper.toDto(updatedCategory);
                })
                .orElse(null);
    }

    private boolean isSlugUnique(String slug) {
        return !categoryRepository.existsBySlug(slug);
    }

    public void deleteCategory(UUID categoryId) {
        ProductCategory category = checkExistCategory(categoryId);
        deleteSubCategoryById(category);
        categoryRepository.delete(category);
    }

    public void deleteSubCategoryById(ProductCategory subCategory) {

        // Remove subcategory from its parent's subcategories set
        if (subCategory.getParentId() != null) {
            ProductCategory parentCategory = categoryRepository.findById(subCategory.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("productCategory", "id", subCategory.getParentId().toString()));
            parentCategory.getSubCategory().remove(subCategory);
        }
    }

    private ProductCategory checkExistCategory(UUID id) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("productCategory", "id", id.toString()));
        checkExistsAllByParentId(id);
        return category;
    }

    private void checkExistsAllByParentId(UUID id) {
        if (categoryRepository.existsAllByParentId(id)){
            throw new RuntimeException("DELETE CATEGORY FAILED BECAUSE CHILD IS EXIST");
        }
    }

    @Override
    protected Specification<ProductCategory> createSpecification(FilterCriteria filter) {
        return new GenericSpecification<>(filter);
    }

    public ResponseDto searchProductCategories(SearchDto requestDto) {
        return search(requestDto, ProductCategoryDto.class);
    }
}
