package com.example.trendybuy.dao.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(
        name = "wishlists",
        schema = "ecommerce",
        uniqueConstraints = {

                @UniqueConstraint(name = "uq_wishlists_user_product", columnNames = {"user_id", "product_id"})
        },
        indexes = {

                @Index(name = "idx_wishlists_user_time", columnList = "user_id, added_at"),

                @Index(name = "idx_wishlists_product", columnList = "product_id")
        }
)
public class WishlistEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_wishlists_user")
    )
    private UserEntity user;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_wishlists_product")
    )
    private ProductEntity product;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;


    @PrePersist
    void prePersist() {
        if (addedAt == null) {
            addedAt = LocalDateTime.now();
        }
    }
}