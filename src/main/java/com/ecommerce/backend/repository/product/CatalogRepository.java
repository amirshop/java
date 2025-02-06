package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
}