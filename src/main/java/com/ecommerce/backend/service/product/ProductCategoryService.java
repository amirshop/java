package com.ecommerce.backend.service.product;

import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.repository.product.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    public List<ProductCategory> getAllCategories() { return productCategoryRepository.findAll(); }
}