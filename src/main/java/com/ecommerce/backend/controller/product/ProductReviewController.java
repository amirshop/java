package com.ecommerce.backend.controller.product;

import com.ecommerce.backend.dto.product.ProductReviewDto;
import com.ecommerce.backend.service.product.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @GetMapping
    public ResponseEntity<List<ProductReviewDto>> getAllReviews(
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID accountId) {
        List<ProductReviewDto> reviews = productReviewService.getAllReviews(productId, accountId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ProductReviewDto> getReviewById(@PathVariable UUID reviewId) {
        ProductReviewDto review = productReviewService.getReviewById(reviewId);
        return review != null
                ? ResponseEntity.ok(review)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductReviewDto> createReview(@RequestBody ProductReviewDto reviewRequest) {
        ProductReviewDto createdReview = productReviewService.createReview(reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ProductReviewDto> updateReview(@PathVariable UUID reviewId,
                                                                 @RequestBody ProductReviewDto reviewRequest) {
        ProductReviewDto updatedReview = productReviewService.updateReview(reviewId, reviewRequest);
        return updatedReview != null
                ? ResponseEntity.ok(updatedReview)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID reviewId) {
        productReviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}

