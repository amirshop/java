package com.ecommerce.backend.entity.product;

import com.ecommerce.backend.entity.account.Account;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    private Account reviewer;
}
