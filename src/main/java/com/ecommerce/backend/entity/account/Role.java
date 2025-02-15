package com.ecommerce.backend.entity.account;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Date createdAt;

  private Date updatedAt;

  @Column(length = 50, nullable = false)
  private String label;

  @Column(length = 50, nullable = false)
  private String value;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "role_permissions",
          joinColumns = @JoinColumn(name = "role_id"),
          inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private Set<Permission> permissions;
}

