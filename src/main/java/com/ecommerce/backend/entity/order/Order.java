package com.ecommerce.backend.entity.order;

import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.entity.payment.Payment;
import com.ecommerce.backend.entity.shipment.Shipment;
import com.ecommerce.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "orders")
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "order_type", discriminatorType = DiscriminatorType.STRING)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;

    private String orderNumber;
    private String orderProcessor;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Payment> payments;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Shipment> shipments;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    private BigDecimal totalAmount;
    private String shippingAddress;
    private String billingAddress;
}
