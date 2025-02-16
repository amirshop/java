package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.order.request.OrderRequestDto;
import com.ecommerce.backend.dto.order.response.OrderResponseDto;
import com.ecommerce.backend.entity.order.Order;
import com.ecommerce.backend.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID orderId) {
        OrderResponseDto order = orderService.getOrderById(orderId);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequest) {
        OrderResponseDto createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable UUID orderId,
                                                        @RequestBody OrderRequestDto orderRequest) {
        OrderResponseDto updatedOrder = orderService.updateOrder(orderId, orderRequest);
        return updatedOrder != null
                ? ResponseEntity.ok(updatedOrder)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}

