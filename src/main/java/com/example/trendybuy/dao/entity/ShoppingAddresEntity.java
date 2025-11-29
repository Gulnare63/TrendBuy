package com.example.trendybuy.dao.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "shipping_address",
        schema = "ecommerce",
        indexes = {

                @Index(name = "idx_shipping_address_user_default", columnList = "user_id, is_default"),

                @Index(name = "idx_shipping_address_user_time", columnList = "user_id, created_at")

        }
)
public class ShoppingAddresEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_shipping_address_user")
    )
    private UserEntity user;


    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;


    @Column(name = "city", length = 100)
    private String city;


//     Poçt indeksi (opsional, max 20).

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "is_default", nullable = false)
    private boolean defaultAddress = false;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
