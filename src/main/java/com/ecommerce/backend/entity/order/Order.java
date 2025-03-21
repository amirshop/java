package com.ecommerce.backend.entity.order;

import com.ecommerce.backend.entity.customer.Customer;
import com.ecommerce.backend.entity.payment.Payment;
import com.ecommerce.backend.entity.shipment.Shipment;
import com.ecommerce.backend.entity.cart.Item;
import com.ecommerce.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private Date updatedAt;

    private String orderNumber;
    private String orderProcessor;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Payment> payments;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Shipment> shipments;

    // If each order has items:
    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
