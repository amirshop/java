package com.ecommerce.backend.controller.product;

import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.BrandDto;
import com.ecommerce.backend.dto.product.TagDto;
import com.ecommerce.backend.entity.product.Brand;
import com.ecommerce.backend.service.product.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable UUID brandId) {
        BrandDto brand = brandService.getBrandById(brandId);
        return brand != null
                ? ResponseEntity.ok(brand)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BrandDto> createBrand(@RequestBody BrandDto brandDto) {
        return ResponseEntity.ok(brandService.createBrand(brandDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDto> updateBrand(@PathVariable UUID id,
                                             @RequestBody BrandDto brandDto) {
        BrandDto updatedBrand = brandService.updateBrand(id, brandDto);
        return updatedBrand != null
                ? ResponseEntity.ok(updatedBrand)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        brandService.deleteBrand(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search")
    public ResponseDto searchBrands(@RequestBody SearchDto requestDto) {
        return brandService.searchBrands(requestDto);
    }
}

