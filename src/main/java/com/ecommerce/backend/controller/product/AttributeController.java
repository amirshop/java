package com.ecommerce.backend.controller.product;

import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.product.AttributeDto;
import com.ecommerce.backend.service.product.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping
    public ResponseEntity<List<AttributeDto>> getAllAttributes() {
        return ResponseEntity.ok(attributeService.getAllAttributes());
    }


    @GetMapping("/{attributeId}")
    public ResponseEntity<AttributeDto> getAttributeById(@PathVariable UUID attributeId) {
        AttributeDto attribute = attributeService.getAttributeById(attributeId);
        return attribute != null
                ? ResponseEntity.ok(attribute)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AttributeDto> createAttribute(@RequestBody AttributeDto attributeDto) {
        return ResponseEntity.ok(attributeService.createAttribute(attributeDto));
    }

    @PutMapping("/{attributeId}")
    public ResponseEntity<AttributeDto> updateAttribute(@PathVariable UUID attributeId,
                                                             @RequestBody AttributeDto attributeDto) {
        AttributeDto updatedAttribute = attributeService.updateAttribute(attributeId, attributeDto);
        return updatedAttribute != null
                ? ResponseEntity.ok(updatedAttribute)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{attributeId}")
    public ResponseEntity<Void> deleteAttribute(@PathVariable UUID attributeId) {
        attributeService.deleteAttribute(attributeId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseDto searchTags(@RequestBody SearchDto requestDto) {
        return attributeService.searchAttributes(requestDto);
    }
}

