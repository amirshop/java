package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.product.ProductCategoryDto;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.repository.product.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    public List<ProductCategoryDto> getAllCategories() {
        List<ProductCategory> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, ProductCategoryDto.class))
                .collect(Collectors.toList());
    }

    public ProductCategoryDto getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> modelMapper.map(category, ProductCategoryDto.class))
                .orElse(null);
    }

    public ProductCategoryDto createCategory(ProductCategoryDto categoryRequest) {
        ProductCategory category = modelMapper.map(categoryRequest, ProductCategory.class);
        ProductCategory saved = categoryRepository.save(category);
        return modelMapper.map(saved, ProductCategoryDto.class);
    }

    public ProductCategoryDto updateCategory(UUID categoryId, ProductCategoryDto categoryRequest) {
        return categoryRepository.findById(categoryId)
                .map(existing -> {
                    existing.setName(categoryRequest.getName());
                    existing.setDescription(categoryRequest.getDescription());
                    ProductCategory updated = categoryRepository.save(existing);
                    return modelMapper.map(updated, ProductCategoryDto.class);
                })
                .orElse(null);
    }

    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
