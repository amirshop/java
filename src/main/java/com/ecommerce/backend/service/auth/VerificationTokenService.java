package com.ecommerce.backend.service.auth;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationTokenService {
    private final RedisTemplate<String, String> redisTemplate;

    // Expiration time in minutes (e.g. 24 hours)
    private final long EXPIRATION_TIME = 60 * 24;

    public VerificationTokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Generate a token and store the account's identifier (e.g., username) as the value
    public String createVerificationToken(String accountIdentifier) {
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, accountIdentifier, EXPIRATION_TIME, TimeUnit.MINUTES);
        return token;
    }

    public String getAccountIdentifierByToken(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    public void deleteVerificationToken(String token) {
        redisTemplate.delete(token);
    }
}
