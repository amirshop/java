package com.ecommerce.backend.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "attribute")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    private String label;  // نام ویژگی (مثلاً "بلوتوث")


    @ElementCollection
    @CollectionTable(name = "attribute_values", joinColumns = @JoinColumn(name = "attribute_id"))
    @Column(name = "attribute_value")
    private List<String> value; // مقدار ویژگی (مثلاً "۵.۳")

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
