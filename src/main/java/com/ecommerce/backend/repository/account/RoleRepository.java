package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.Permission;
import com.ecommerce.backend.entity.account.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {
  Optional<Role> findByLabel(String label);
  Boolean existsByValue(String value);

}
