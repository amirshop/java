package com.ecommerce.backend.repository.order;

import com.ecommerce.backend.entity.order.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {
}
