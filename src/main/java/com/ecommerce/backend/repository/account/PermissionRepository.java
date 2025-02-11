package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.Permission;
import com.ecommerce.backend.entity.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
