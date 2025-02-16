package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.product.request.ProductRequestDto;
import com.ecommerce.backend.dto.product.response.ProductResponseDto;
import com.ecommerce.backend.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID categoryId) {
        List<ProductResponseDto> products = productService.getAllProducts(name, categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return product != null
                ? ResponseEntity.ok(product)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequest) {
        ProductResponseDto createdProduct = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID productId,
                                                            @RequestBody ProductRequestDto productRequest) {
        ProductResponseDto updatedProduct = productService.updateProduct(productId, productRequest);
        return updatedProduct != null
                ? ResponseEntity.ok(updatedProduct)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
