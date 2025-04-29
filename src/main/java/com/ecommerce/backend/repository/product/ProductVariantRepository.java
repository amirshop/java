package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID>,
        JpaSpecificationExecutor<ProductVariant> {
   List<ProductVariant> findAllByProductId(UUID productId);
   void deleteAllByProductId(UUID productId);
}
