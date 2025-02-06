package com.ecommerce.backend.entity.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Entity
@DiscriminatorValue("ORDER_LOG")
@Data
public class OrderLog extends Order {
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    // Possibly track oldStatus or reason for change
}
