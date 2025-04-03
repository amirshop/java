package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "product_variant")
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Double regularPrice;

    private Double salePrice;

    private Date salePriceFromAt;
    private Date salePriceToAt;

    @Column(nullable = false)
    private int availableItemCount;

    // Link back to the base product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Association to the variant's attributes (e.g., color, size, etc.)
    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariantAttribute> attributes;
}

