package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.product.request.ProductCategoryRequestDto;
import com.ecommerce.backend.dto.product.response.ProductCategoryResponseDto;
import com.ecommerce.backend.service.product.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategoryResponseDto>> getAllCategories() {
        List<ProductCategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ProductCategoryResponseDto> getCategoryById(@PathVariable Long categoryId) {
        ProductCategoryResponseDto category = categoryService.getCategoryById(categoryId);
        return category != null
                ? ResponseEntity.ok(category)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductCategoryResponseDto> createCategory(@RequestBody ProductCategoryRequestDto categoryRequest) {
        ProductCategoryResponseDto createdCategory = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ProductCategoryResponseDto> updateCategory(@PathVariable Long categoryId,
                                                                     @RequestBody ProductCategoryRequestDto categoryRequest) {
        ProductCategoryResponseDto updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return updatedCategory != null
                ? ResponseEntity.ok(updatedCategory)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
