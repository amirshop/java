package com.ecommerce.backend.entity.cart;

import com.ecommerce.backend.entity.account.Account;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;
    private Date updatedAt;

//    private boolean isClosed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // A shopping cart can have many items
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<Item> items;
}
