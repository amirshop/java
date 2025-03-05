package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.product.ProductReviewDto;
import com.ecommerce.backend.entity.product.ProductReview;
import com.ecommerce.backend.repository.product.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository reviewRepository;

    private final ModelMapper modelMapper;

    public List<ProductReviewDto> getAllReviews(UUID productId, UUID accountId) {
        List<ProductReview> reviews;
        if (productId != null) {
            reviews = reviewRepository.findByProductId(productId);
        } else if (accountId != null) {
            reviews = reviewRepository.findByReviewerId(accountId);
        } else {
            reviews = reviewRepository.findAll();
        }
        return reviews.stream()
                .map(review -> modelMapper.map(review, ProductReviewDto.class))
                .collect(Collectors.toList());
    }

    public ProductReviewDto getReviewById(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> modelMapper.map(review, ProductReviewDto.class))
                .orElse(null);
    }

    public ProductReviewDto createReview(ProductReviewDto reviewRequest) {
        ProductReview review = modelMapper.map(reviewRequest, ProductReview.class);
        ProductReview saved = reviewRepository.save(review);
        return modelMapper.map(saved, ProductReviewDto.class);
    }

    public ProductReviewDto updateReview(UUID reviewId, ProductReviewDto reviewRequest) {
        return reviewRepository.findById(reviewId)
                .map(existing -> {
                    existing.setRating(reviewRequest.getRating());
                    existing.setReviewText(reviewRequest.getReviewText());
                    ProductReview updated = reviewRepository.save(existing);
                    return modelMapper.map(updated, ProductReviewDto.class);
                })
                .orElse(null);
    }

    public void deleteReview(UUID reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
