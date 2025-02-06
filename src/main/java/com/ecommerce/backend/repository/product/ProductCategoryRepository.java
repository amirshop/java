package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
