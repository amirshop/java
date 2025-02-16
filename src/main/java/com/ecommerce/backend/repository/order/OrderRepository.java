package com.ecommerce.backend.repository.order;

import com.ecommerce.backend.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
