package com.ecommerce.backend.controller.product;


import com.ecommerce.backend.dto.product.ProductVariantDto;
import com.ecommerce.backend.service.product.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/variants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService variantService;

    @PostMapping
    public ResponseEntity<ProductVariantDto> createVariant(@RequestBody ProductVariantDto variantDTO) {
        ProductVariantDto savedVariantDTO = variantService.saveVariant(variantDTO);
        return ResponseEntity.ok(savedVariantDTO);
    }

    @GetMapping("/{variantId}")
    public ResponseEntity<ProductVariantDto> getVariant(@PathVariable UUID variantId) {
        return variantService.getVariantById(variantId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public List<ProductVariantDto> getVariantsByProductId(@PathVariable UUID productId) {
        return variantService.getVariantsByProductId(productId);
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable UUID variantId) {
        variantService.deleteVariant(variantId);
        return ResponseEntity.noContent().build();
    }
}

