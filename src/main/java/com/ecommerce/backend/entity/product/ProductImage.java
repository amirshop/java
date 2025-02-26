package com.ecommerce.backend.entity.product;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "product_image")
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String imageUrl; // لینک ذخیره‌شده تصویر

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // محصولی که این تصویر به آن تعلق دارد
}
