package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.product.request.ProductReviewRequestDto;
import com.ecommerce.backend.dto.product.response.ProductReviewResponseDto;
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
    public ResponseEntity<List<ProductReviewResponseDto>> getAllReviews(
            @RequestParam(required = false) UUID productId,
            @RequestParam(required = false) UUID accountId) {
        List<ProductReviewResponseDto> reviews = productReviewService.getAllReviews(productId, accountId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ProductReviewResponseDto> getReviewById(@PathVariable UUID reviewId) {
        ProductReviewResponseDto review = productReviewService.getReviewById(reviewId);
        return review != null
                ? ResponseEntity.ok(review)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductReviewResponseDto> createReview(@RequestBody ProductReviewRequestDto reviewRequest) {
        ProductReviewResponseDto createdReview = productReviewService.createReview(reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ProductReviewResponseDto> updateReview(@PathVariable UUID reviewId,
                                                                 @RequestBody ProductReviewRequestDto reviewRequest) {
        ProductReviewResponseDto updatedReview = productReviewService.updateReview(reviewId, reviewRequest);
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

