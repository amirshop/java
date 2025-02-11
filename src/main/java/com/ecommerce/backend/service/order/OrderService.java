package com.ecommerce.backend.service.order;

import com.ecommerce.backend.dto.order.request.OrderRequestDto;
import com.ecommerce.backend.dto.order.response.OrderResponseDto;
import com.ecommerce.backend.entity.order.Order;
import com.ecommerce.backend.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService  {

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());
    }

    public OrderResponseDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .orElse(null);
    }

    public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        order.setOrderNumber("ORD-" + System.currentTimeMillis()); // Generate order number
        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderResponseDto.class);
    }

    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequest) {
        return orderRepository.findById(orderId)
                .map(existing -> {
                    existing.setOrderProcessor(orderRequest.getOrderProcessor());
                    existing.setStatus(orderRequest.getStatus());
                    existing.setOrderDate(orderRequest.getOrderDate());
                    Order updated = orderRepository.save(existing);
                    return modelMapper.map(updated, OrderResponseDto.class);
                })
                .orElse(null);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}

