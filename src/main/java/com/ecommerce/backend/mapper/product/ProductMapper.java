package com.ecommerce.backend.mapper.product;

import com.ecommerce.backend.dto.product.ProductDto;
import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductCategory;
import com.ecommerce.backend.entity.product.ProductReview;
import com.ecommerce.backend.entity.product.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productReviews", source = "productReviews", qualifiedByName = "mapUUIDsToProductReviews")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapUUIDsToTags")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapUUIDsToProductCategories")
    @Mapping(target = "brand.id", source = "brandId")
    Product toEntity(ProductDto productDto);

    @Mapping(target = "productReviews", source = "productReviews", qualifiedByName = "mapProductReviewsToUUIDs")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTagsToUUIDs")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "mapProductCategoriesToUUIDs")
    @Mapping(target = "brandId", source = "brand.id")
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

    @Named("mapUUIDsToTags")
    default Set<Tag> mapUUIDsToTags(Set<UUID> tagIds) {
        if (tagIds == null) {
            return new HashSet<>();
        }

        // Here, you can fetch ProductReview entities from the DB using review IDs
        return tagIds.stream()
                .map(id -> {
                    Tag tag = new Tag();
                    tag.setId(id);
                    return tag;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapTagsToUUIDs")
    default Set<UUID> mapTagsToUUIDs(Set<Tag> tags) {
        if (tags == null) {
            return new HashSet<>();
        }

        // Extract only the IDs of the reviews
        return tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapUUIDsToProductCategories")
    default Set<ProductCategory> mapUUIDsToProductCategories(Set<UUID> productCategoriesIds) {
        if (productCategoriesIds == null) {
            return new HashSet<>();
        }

        // Here, you can fetch ProductReview entities from the DB using review IDs
        return productCategoriesIds.stream()
                .map(id -> {
                    ProductCategory productCategory = new ProductCategory();
                    productCategory.setId(id);
                    return productCategory;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapProductCategoriesToUUIDs")
    default Set<UUID> mapProductCategoriesToUUIDs(Set<ProductCategory> productCategories) {
        if (productCategories == null) {
            return new HashSet<>();
        }

        // Extract only the IDs of the reviews
        return productCategories.stream()
                .map(ProductCategory::getId)
                .collect(Collectors.toSet());
    }
}


