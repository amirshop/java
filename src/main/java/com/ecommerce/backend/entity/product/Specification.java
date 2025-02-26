package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "specification")
@AllArgsConstructor
@NoArgsConstructor
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date createdAt;
    private Date updatedAt;

    private String key;  // نام ویژگی (مثلاً "بلوتوث")
    private String value; // مقدار ویژگی (مثلاً "۵.۳")

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
