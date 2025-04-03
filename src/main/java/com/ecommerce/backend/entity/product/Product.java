package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReview> productReviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Attribute> attributes; // مشخصات

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images; // لیست تصاویر محصول

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToMany
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<ProductCategory> categories = new HashSet<>();


    // Association to product variants
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;


    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}

