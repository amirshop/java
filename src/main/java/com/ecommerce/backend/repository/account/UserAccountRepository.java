package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID>, JpaSpecificationExecutor<UserAccount> {
  Optional<UserAccount> findByUsername(String username);
  Optional<UserAccount> findByEmail(String email);

  Optional<UserAccount> findByUsernameOrEmail(String userName, String email);
//  Optional<Account> findByVerificationToken(String token);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByPhone(String email);
}
