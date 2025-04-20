package com.ecommerce.backend.controller.product;

import com.ecommerce.backend.dto.product.TagDto;
import com.ecommerce.backend.service.product.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }


    @GetMapping("/{tagId}")
    public ResponseEntity<TagDto> getTagById(@PathVariable UUID tagId) {
        TagDto category = tagService.getTagById(tagId);
        return category != null
                ? ResponseEntity.ok(category)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.createTag(tagDto));
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagDto> updateTag(@PathVariable UUID tagId,
                                                             @RequestBody TagDto tagDto) {
        TagDto updatedTag = tagService.updateTag(tagId, tagDto);
        return updatedTag != null
                ? ResponseEntity.ok(updatedTag)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}

