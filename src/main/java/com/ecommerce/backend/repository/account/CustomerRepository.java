package com.ecommerce.backend.repository.account;

import com.ecommerce.backend.entity.account.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
