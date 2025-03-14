package com.ecommerce.backend.entity.account;

import com.ecommerce.backend.enums.ColorsEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop_setting")
public class ShopSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;

    private String logo;

    private String favicon;

    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColorsEnum primaryColor;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", unique = true)
    private Account account;
}

