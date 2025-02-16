package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByCategory(ProductCategory category);
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByNameContainingAndCategoryId(String name, UUID categoryId);
}
