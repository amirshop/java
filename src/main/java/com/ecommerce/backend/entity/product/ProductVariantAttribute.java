package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "product_variant_attribute")
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Name of the attribute (e.g., "color", "size", "material", etc.)
    @Column(nullable = false)
    private String attributeName;

    // Value of the attribute (e.g., "red", "XL", "cotton")
    @Column(nullable = false)
    private String attributeValue;

    // Link back to the variant
    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;
}

