package com.ecommerce.backend.controller.product;

import com.ecommerce.backend.dto.product.ProductVariantAttributeDto;
import com.ecommerce.backend.service.product.ProductVariantAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/variant-attributes")
public class ProductVariantAttributeController {

    @Autowired
    private ProductVariantAttributeService attributeService;

    @PostMapping
    public ResponseEntity<ProductVariantAttributeDto> createAttribute(@RequestBody ProductVariantAttributeDto attributeDTO) {
        ProductVariantAttributeDto savedAttributeDTO = attributeService.saveAttribute(attributeDTO);
        return ResponseEntity.ok(savedAttributeDTO);
    }

    @GetMapping("/{attributeId}")
    public ResponseEntity<ProductVariantAttributeDto> getAttribute(@PathVariable UUID attributeId) {
        return attributeService.getAttributeById(attributeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/variant/{variantId}")
    public List<ProductVariantAttributeDto> getAttributesByVariantId(@PathVariable UUID variantId) {
        return attributeService.getAttributesByVariantId(variantId);
    }

    @DeleteMapping("/{attributeId}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable UUID attributeId) {
        attributeService.deleteAttribute(attributeId);
        return ResponseEntity.noContent().build();
    }
}

