package com.ecommerce.backend.service.order;

import com.ecommerce.backend.entity.order.OrderLog;
import com.ecommerce.backend.repository.order.OrderLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLogService {
    private final OrderLogRepository orderLogRepository;
    public List<OrderLog> getAllOrderLogs() { return orderLogRepository.findAll(); }
}