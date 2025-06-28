package com.ecommerce.backend.entity.order;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private UUID variantId;
    private String productName;
    private String variantName;
    private String sku;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
