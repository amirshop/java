package com.ecommerce.backend.mapper.product;

import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productReviews", source = "productReviews", qualifiedByName = "mapUUIDsToProductReviews")
    Product toEntity(ProductDto productDto);

    @Mapping(target = "productReviews", source = "productReviews", qualifiedByName = "mapProductReviewsToUUIDs")
    ProductDto toDto(Product product);

    @Named("mapUUIDsToProductReviews")
    default List<ProductReview> mapUUIDsToProductReviews(List<UUID> reviewIds) {
        if (reviewIds == null) {
            return new ArrayList<>();
        }

        // Here, you can fetch ProductReview entities from the DB using review IDs
        return reviewIds.stream()
                .map(id -> {
                    ProductReview review = new ProductReview();
                    review.setId(id);
                    return review;
                })
                .collect(Collectors.toList());
    }

    @Named("mapProductReviewsToUUIDs")
    default List<UUID> mapProductReviewsToUUIDs(List<ProductReview> reviews) {
        if (reviews == null) {
            return new ArrayList<>();
        }

        // Extract only the IDs of the reviews
        return reviews.stream()
                .map(ProductReview::getId)
                .collect(Collectors.toList());
    }
}


