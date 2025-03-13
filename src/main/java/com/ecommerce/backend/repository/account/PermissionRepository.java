package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.Permission;
import com.ecommerce.backend.entity.account.Role;
import com.ecommerce.backend.entity.product.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID>, JpaSpecificationExecutor<Permission> {
}
