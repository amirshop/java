package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.product.request.ProductCategoryRequestDto;
import com.ecommerce.backend.dto.product.response.ProductCategoryResponseDto;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.repository.product.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    public List<ProductCategoryResponseDto> getAllCategories() {
        List<ProductCategory> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, ProductCategoryResponseDto.class))
                .collect(Collectors.toList());
    }

    public ProductCategoryResponseDto getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> modelMapper.map(category, ProductCategoryResponseDto.class))
                .orElse(null);
    }

    public ProductCategoryResponseDto createCategory(ProductCategoryRequestDto categoryRequest) {
        ProductCategory category = modelMapper.map(categoryRequest, ProductCategory.class);
        ProductCategory saved = categoryRepository.save(category);
        return modelMapper.map(saved, ProductCategoryResponseDto.class);
    }

    public ProductCategoryResponseDto updateCategory(Long categoryId, ProductCategoryRequestDto categoryRequest) {
        return categoryRepository.findById(categoryId)
                .map(existing -> {
                    existing.setName(categoryRequest.getName());
                    existing.setDescription(categoryRequest.getDescription());
                    ProductCategory updated = categoryRepository.save(existing);
                    return modelMapper.map(updated, ProductCategoryResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
