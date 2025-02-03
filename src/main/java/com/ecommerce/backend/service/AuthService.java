package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.LoginRequest;
import com.ecommerce.backend.dto.RegisterRequest;
import com.ecommerce.backend.entity.Account;
import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.entity.VerificationToken;
import com.ecommerce.backend.enums.AccountStatus;
import com.ecommerce.backend.jwt.JwtUtils;
import com.ecommerce.backend.repository.auth.AccountRepository;
import com.ecommerce.backend.repository.auth.RoleRepository;
import com.ecommerce.backend.repository.auth.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtUtils jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

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

        // Create verification token and save it
        VerificationToken token = new VerificationToken(account);
        verificationTokenRepository.save(token);

        // TODO: Send an email with the verification link that includes token.getToken()
        // For example: http://localhost:8080/api/auth/verify?token=token.getToken()
    }

    public void verify(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        Account account = verificationToken.getAccount();
        // Update status to ACTIVE upon successful verification
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        verificationTokenRepository.delete(verificationToken);
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
