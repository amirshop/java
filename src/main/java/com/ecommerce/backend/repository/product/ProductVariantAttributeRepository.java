package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.ProductVariantAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ProductVariantAttributeRepository  extends JpaRepository<ProductVariantAttribute, UUID>,
        JpaSpecificationExecutor<ProductVariantAttribute> {
   List<ProductVariantAttribute> findAllByVariantId(UUID variantId);
}
