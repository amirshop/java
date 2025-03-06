package com.ecommerce.backend.service.order;

import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.entity.order.Order;
import com.ecommerce.backend.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService  {

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(order -> modelMapper.map(order, OrderDto.class))
                .orElse(null);
    }

    public OrderDto createOrder(OrderDto orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        order.setOrderNumber("ORD-" + System.currentTimeMillis()); // Generate order number
        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderDto.class);
    }

    public OrderDto updateOrder(UUID orderId, OrderDto orderRequest) {
        return orderRepository.findById(orderId)
                .map(existing -> {
                    existing.setOrderProcessor(orderRequest.getOrderProcessor());
                    existing.setStatus(orderRequest.getStatus());
                    existing.setOrderDate(orderRequest.getOrderDate());
                    Order updated = orderRepository.save(existing);
                    return modelMapper.map(updated, OrderDto.class);
                })
                .orElse(null);
    }

    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}

