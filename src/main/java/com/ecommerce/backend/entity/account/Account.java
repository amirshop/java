package com.ecommerce.backend.entity.account;

import com.ecommerce.backend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.UNKNOWN;

    @Embedded
    private Address shippingAddress;

    // In a real app, you might have roles, etc.
    // Omitted for brevity, or add if needed:
    // @ManyToMany(...) private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}