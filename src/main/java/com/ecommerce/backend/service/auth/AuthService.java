package com.ecommerce.backend.service.auth;

import com.ecommerce.backend.dto.auth.LoginRequest;
import com.ecommerce.backend.dto.auth.RegisterRequest;
import com.ecommerce.backend.entity.account.Account;
import com.ecommerce.backend.entity.account.Role;
import com.ecommerce.backend.enums.AccountStatus;
import com.ecommerce.backend.jwt.JwtUtils;
import com.ecommerce.backend.repository.account.AccountRepository;
import com.ecommerce.backend.repository.account.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenService verificationTokenService;
    private final JwtUtils jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        // Check if account exists
        if (accountRepository.findByUsername(request.getUserName()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // Create and save account
        Account account = new Account();
        account.setUsername(request.getUserName());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setStatus(AccountStatus.UNKNOWN);  // default status before verification

        // Set default role (assumes a ROLE_USER exists in your database)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        account.setRoles(Collections.singleton(userRole));

        accountRepository.save(account);

        // Create verification token and store in Redis
        String token = verificationTokenService.createVerificationToken(account.getUsername());

        // TODO: Send an email with the verification link that includes token.getToken()
        // For example: http://localhost:8080/api/auth/verify?token=token.getToken()
    }

    public void verify(String token) {
        // Get the associated account identifier (userName) from Redis
        String accountIdentifier = verificationTokenService.getAccountIdentifierByToken(token);
        if (accountIdentifier == null) {
            throw new RuntimeException("Invalid or expired token");
        }
        // Retrieve the account by username
        Account account = accountRepository.findByUsername(accountIdentifier)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update status to ACTIVE upon successful verification
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        verificationTokenService.deleteVerificationToken(token);
    }

    public String login(LoginRequest request) {
        Account account = accountRepository.findByUsername(request.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Check if account is verified (ACTIVE)
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Account not verified");
        }

        // Generate and return JWT token
        return jwtTokenProvider.generateToken(account.getUsername());
    }
}
