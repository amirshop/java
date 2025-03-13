package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.Account;
import com.ecommerce.backend.entity.account.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {
  Optional<Account> findByUsername(String username);
  Optional<Account> findByEmail(String email);

  Optional<Account> findByUsernameOrEmail(String userName, String email);
//  Optional<Account> findByVerificationToken(String token);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByPhone(String email);
}
