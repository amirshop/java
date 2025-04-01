package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID>, JpaSpecificationExecutor<ProductCategory> {
    boolean existsBySlug(String slug);
}
