package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CatalogRepository extends JpaRepository<Catalog, UUID> {
}