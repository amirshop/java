package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.account.Permission;
import com.ecommerce.backend.entity.product.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID>, JpaSpecificationExecutor<Tag> {
    Optional<Tag> findByName(String name);
}
