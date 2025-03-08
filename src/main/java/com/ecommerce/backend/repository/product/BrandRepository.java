package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.Brand;
import com.ecommerce.backend.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID>, JpaSpecificationExecutor<Brand> {
    // جستجو بر اساس نام برند
    Optional<Brand> findByName(String name);
}