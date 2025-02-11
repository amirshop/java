package com.ecommerce.backend.service.product;

import com.ecommerce.backend.dto.product.request.ProductReviewRequestDto;
import com.ecommerce.backend.dto.product.response.ProductReviewResponseDto;
import com.ecommerce.backend.entity.product.ProductReview;
import com.ecommerce.backend.repository.product.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewService {

    private final ProductReviewRepository reviewRepository;

    private final ModelMapper modelMapper;

    public List<ProductReviewResponseDto> getAllReviews(Long productId, Long accountId) {
        List<ProductReview> reviews;
        if (productId != null) {
            reviews = reviewRepository.findByProductId(productId);
        } else if (accountId != null) {
            reviews = reviewRepository.findByReviewerId(accountId);
        } else {
            reviews = reviewRepository.findAll();
        }
        return reviews.stream()
                .map(review -> modelMapper.map(review, ProductReviewResponseDto.class))
                .collect(Collectors.toList());
    }

    public ProductReviewResponseDto getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> modelMapper.map(review, ProductReviewResponseDto.class))
                .orElse(null);
    }

    public ProductReviewResponseDto createReview(ProductReviewRequestDto reviewRequest) {
        ProductReview review = modelMapper.map(reviewRequest, ProductReview.class);
        ProductReview saved = reviewRepository.save(review);
        return modelMapper.map(saved, ProductReviewResponseDto.class);
    }

    public ProductReviewResponseDto updateReview(Long reviewId, ProductReviewRequestDto reviewRequest) {
        return reviewRepository.findById(reviewId)
                .map(existing -> {
                    existing.setRating(reviewRequest.getRating());
                    existing.setReviewText(reviewRequest.getReviewText());
                    ProductReview updated = reviewRepository.save(existing);
                    return modelMapper.map(updated, ProductReviewResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
