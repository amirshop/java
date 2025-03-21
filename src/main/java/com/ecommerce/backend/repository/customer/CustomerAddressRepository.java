package com.ecommerce.backend.repository.customer;


import java.util.UUID;

import com.ecommerce.backend.entity.customer.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, UUID>, JpaSpecificationExecutor<CustomerAddress> {
}

