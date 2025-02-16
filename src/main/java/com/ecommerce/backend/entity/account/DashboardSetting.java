package com.ecommerce.backend.entity.account;

import com.ecommerce.backend.enums.ColorsEnum;
import com.ecommerce.backend.enums.ComponentsSizesEnum;
import com.ecommerce.backend.enums.DirectionsEnum;
import com.ecommerce.backend.enums.LanguagesEnum;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "dashboard_setting")
public class DashboardSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;
    private String logo;
    private String favicon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColorsEnum primaryColor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DirectionsEnum direction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LanguagesEnum language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComponentsSizesEnum size;

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

