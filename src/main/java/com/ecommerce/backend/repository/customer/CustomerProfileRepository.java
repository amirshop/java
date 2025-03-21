package com.ecommerce.backend.repository.customer;


import java.util.UUID;

import com.ecommerce.backend.entity.customer.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, UUID> {
}

