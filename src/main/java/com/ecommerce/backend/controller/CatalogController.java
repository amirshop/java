package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.product.CatalogDto;
import com.ecommerce.backend.service.product.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping
    public ResponseEntity<List<CatalogDto>> getAllCatalogs() {
        List<CatalogDto> catalogs = catalogService.getAllCatalogs();
        return ResponseEntity.ok(catalogs);
    }

    @GetMapping("/{catalogId}")
    public ResponseEntity<CatalogDto> getCatalogById(@PathVariable UUID catalogId) {
        CatalogDto catalog = catalogService.getCatalogById(catalogId);
        return catalog != null
                ? ResponseEntity.ok(catalog)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CatalogDto> createCatalog(@RequestBody CatalogDto catalogRequest) {
        CatalogDto createdCatalog = catalogService.createCatalog(catalogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCatalog);
    }

    @PutMapping("/{catalogId}")
    public ResponseEntity<CatalogDto> updateCatalog(@PathVariable UUID catalogId,
                                                            @RequestBody CatalogDto catalogRequest) {
        CatalogDto updatedCatalog = catalogService.updateCatalog(catalogId, catalogRequest);
        return updatedCatalog != null
                ? ResponseEntity.ok(updatedCatalog)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{catalogId}")
    public ResponseEntity<Void> deleteCatalog(@PathVariable UUID catalogId) {
        catalogService.deleteCatalog(catalogId);
        return ResponseEntity.noContent().build();
    }
}
