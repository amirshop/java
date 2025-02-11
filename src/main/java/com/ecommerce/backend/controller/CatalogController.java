package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.product.request.CatalogRequestDto;
import com.ecommerce.backend.dto.product.response.CatalogResponseDto;
import com.ecommerce.backend.service.product.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<List<CatalogResponseDto>> getAllCatalogs() {
        List<CatalogResponseDto> catalogs = catalogService.getAllCatalogs();
        return ResponseEntity.ok(catalogs);
    }

    @GetMapping("/{catalogId}")
    public ResponseEntity<CatalogResponseDto> getCatalogById(@PathVariable Long catalogId) {
        CatalogResponseDto catalog = catalogService.getCatalogById(catalogId);
        return catalog != null
                ? ResponseEntity.ok(catalog)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CatalogResponseDto> createCatalog(@RequestBody CatalogRequestDto catalogRequest) {
        CatalogResponseDto createdCatalog = catalogService.createCatalog(catalogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCatalog);
    }

    @PutMapping("/{catalogId}")
    public ResponseEntity<CatalogResponseDto> updateCatalog(@PathVariable Long catalogId,
                                                            @RequestBody CatalogRequestDto catalogRequest) {
        CatalogResponseDto updatedCatalog = catalogService.updateCatalog(catalogId, catalogRequest);
        return updatedCatalog != null
                ? ResponseEntity.ok(updatedCatalog)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{catalogId}")
    public ResponseEntity<Void> deleteCatalog(@PathVariable Long catalogId) {
        catalogService.deleteCatalog(catalogId);
        return ResponseEntity.noContent().build();
    }
}
