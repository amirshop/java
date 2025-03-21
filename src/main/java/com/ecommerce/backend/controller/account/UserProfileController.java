package com.ecommerce.backend.controller.account;

import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.UserProfileDto;
import com.ecommerce.backend.entity.account.UserProfile;
import com.ecommerce.backend.service.account.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public List<UserProfileDto> getAllProfiles() {
        return userProfileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public UserProfileDto getProfileById(@PathVariable UUID id) {
        return userProfileService.getProfileById(id);
    }

    @PostMapping
    public UserProfileDto createProfile(@Valid @RequestBody UserProfileDto account) {
        return userProfileService.createProfile(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateProfile(@PathVariable UUID id, @RequestBody UserProfileDto updatedAccount) {
        try {
            return ResponseEntity.ok(userProfileService.updateProfile(id, updatedAccount));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        userProfileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseDto searchProfiles(@RequestBody SearchDto requestDto) {
        return userProfileService.searchProfiles(requestDto);
    }
}

