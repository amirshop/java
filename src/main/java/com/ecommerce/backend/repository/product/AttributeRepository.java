package com.ecommerce.backend.repository.product;

import com.ecommerce.backend.entity.product.Attribute;
import com.ecommerce.backend.entity.product.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AttributeRepository extends JpaRepository<Attribute, UUID>, JpaSpecificationExecutor<Attribute> {
}
