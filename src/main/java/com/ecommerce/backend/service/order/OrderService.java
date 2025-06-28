package com.ecommerce.backend.service.order;

import com.ecommerce.backend.dto.order.OrderDto;
import com.ecommerce.backend.entity.cart.Cart;
import com.ecommerce.backend.entity.cart.CartItem;
import com.ecommerce.backend.entity.order.Order;
import com.ecommerce.backend.entity.order.OrderItem;
import com.ecommerce.backend.entity.product.ProductVariant;
import com.ecommerce.backend.mapper.order.OrderMapper;
import com.ecommerce.backend.repository.order.OrderRepository;
import com.ecommerce.backend.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService  {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;

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
                    Order updated = orderRepository.save(existing);
                    return modelMapper.map(updated, OrderDto.class);
                })
                .orElse(null);
    }

    @Transactional
    public OrderDto submitOrder(UUID cartId, String shippingAddress, String billingAddress) {
        Cart cart = cartService.findCartById(cartId);
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
//            OrderItem orderItem = new OrderItem(cartItem.getVariant(), cartItem.getQuantity());
            OrderItem orderItem = new OrderItem();
            addOrderItem(orderItem, cartItem.getVariant(), cartItem.getQuantity());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
            total = total.add(orderItem.getTotalPrice());
        }
        order.setTotalAmount(total);
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private void addOrderItem(OrderItem orderItem, ProductVariant variant, int qty) {
        orderItem.setVariantId(variant.getId());
        orderItem.setProductName(variant.getProduct().getName());
        orderItem.setQuantity(qty);
//        orderItem.setUnitPrice(variant.getSalePrice());

//        this.unitPrice = variant.getPrice();
//        this.quantity = qty;
//        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(qty));
    }

    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}

