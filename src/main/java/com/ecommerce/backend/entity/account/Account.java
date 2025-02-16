package com.ecommerce.backend.entity.account;

import com.ecommerce.backend.entity.cart.Cart;
import com.ecommerce.backend.entity.product.ProductReview;
import com.ecommerce.backend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;
    private Date updatedAt;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String firstname;
    private String lastname;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.UNKNOWN;

    @Embedded
    private Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<ProductReview> accountReviews;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}