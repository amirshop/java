package com.ecommerce.backend.controller.account;

import com.ecommerce.backend.dto.ResponseDto;
import com.ecommerce.backend.dto.SearchDto;
import com.ecommerce.backend.dto.account.UserAccountDto;
import com.ecommerce.backend.entity.account.UserAccount;
import com.ecommerce.backend.service.account.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final UserAccountService accountService;

    @GetMapping
    public List<UserAccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public UserAccountDto getAccountById(@PathVariable UUID id) {
        return accountService.getAccountById(id);
    }

    @PostMapping
    public UserAccountDto createAccount(@Valid @RequestBody UserAccountDto account) {
        return accountService.createAccount(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccountDto> updateAccount(@PathVariable UUID id, @RequestBody UserAccountDto updatedAccount) {
        try {
            return ResponseEntity.ok(accountService.updateAccount(id, updatedAccount));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseDto searchAccounts(@RequestBody SearchDto requestDto) {
        return accountService.searchAccounts(requestDto);
    }
}

