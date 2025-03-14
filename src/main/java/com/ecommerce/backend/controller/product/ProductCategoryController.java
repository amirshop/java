package com.ecommerce.backend.controller.product;

import com.ecommerce.backend.dto.product.ProductCategoryDto;
import com.ecommerce.backend.service.product.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategoryDto>> getAllCategories() {
        List<ProductCategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ProductCategoryDto> getCategoryById(@PathVariable UUID categoryId) {
        ProductCategoryDto category = categoryService.getCategoryById(categoryId);
        return category != null
                ? ResponseEntity.ok(category)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductCategoryDto> createCategory(@RequestBody ProductCategoryDto categoryRequest) {
        ProductCategoryDto createdCategory = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ProductCategoryDto> updateCategory(@PathVariable UUID categoryId,
                                                                     @RequestBody ProductCategoryDto categoryRequest) {
        ProductCategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return updatedCategory != null
                ? ResponseEntity.ok(updatedCategory)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
