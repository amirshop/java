package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductReviewRepository extends JpaRepository<ProductReview, UUID> {

    List<ProductReview> findByProductId(UUID productId);

    List<ProductReview> findByReviewerId(UUID accountId);
}
