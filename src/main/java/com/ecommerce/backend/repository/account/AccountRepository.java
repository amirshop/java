package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByUsername(String username);
  Optional<Account> findByEmail(String email);

  Optional<Account> findByUsernameOrEmail(String userName, String email);
  Optional<Account> findByVerificationToken(String token);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
