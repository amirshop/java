package com.ecommerce.backend.controller.product;

import com.ecommerce.backend.service.product.ProductImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {

    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    // ✅ ۱. آپلود تصویر برای محصول
    @PostMapping("/{productId}")
    public ResponseEntity<String> uploadImage(@PathVariable UUID productId,
                                              @RequestParam MultipartFile file) {
        String imageUrl = productImageService.uploadImage(productId, file);
        return ResponseEntity.ok(imageUrl);
    }

    // ✅ ۲. دریافت لیست تصاویر یک محصول
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<String>> getProductImages(@PathVariable UUID productId) {
        return ResponseEntity.ok(productImageService.getProductImages(productId));
    }

    // ✅ ۳. حذف تصویر بر اساس `imageId`
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID imageId) {
        productImageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

    // ✅ ۴. جایگزینی تصویر بر اساس `imageId`
    @PutMapping("/{imageId}")
    public ResponseEntity<String> updateImage(@PathVariable UUID imageId,
                                              @RequestParam MultipartFile file) {
        String newImageUrl = productImageService.updateImage(imageId, file);
        return ResponseEntity.ok(newImageUrl);
    }
}

