package com.ecommerce.backend.service.product;

import com.ecommerce.backend.entity.product.Product;
import com.ecommerce.backend.entity.product.ProductImage;
import com.ecommerce.backend.repository.product.ProductImageRepository;
import com.ecommerce.backend.repository.product.ProductRepository;
import com.ecommerce.backend.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final MinioService minioService;
    private final ProductRepository productRepository;

    // ✅ ۱. آپلود تصویر جدید و ثبت در دیتابیس
    public String uploadImage(UUID productId, MultipartFile file) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("محصول پیدا نشد!"));

        String imageUrl = minioService.uploadFile(file, productId);

        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setImageUrl(imageUrl);

        productImageRepository.save(productImage);
        return imageUrl;
    }

    // ✅ ۲. دریافت لیست تصاویر محصول
    public List<String> getProductImages(UUID productId) {
        return productImageRepository.findByProductId(productId)
                .stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());
    }

    // ✅ ۳. حذف تصویر بر اساس `imageId`
    public void deleteImage(UUID imageId) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("تصویر یافت نشد!"));

        minioService.deleteFile(productImage.getImageUrl());
        productImageRepository.delete(productImage);
    }

    // ✅ ۴. ویرایش (جایگزینی) تصویر
    public String updateImage(UUID imageId, MultipartFile file) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("تصویر یافت نشد!"));

        minioService.deleteFile(productImage.getImageUrl());
        String newImageUrl = minioService.uploadFile(file, productImage.getProduct().getId());

        productImage.setImageUrl(newImageUrl);
        productImageRepository.save(productImage);
        return newImageUrl;
    }
}

