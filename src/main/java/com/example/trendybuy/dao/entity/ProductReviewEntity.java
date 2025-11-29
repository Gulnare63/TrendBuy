package com.example.trendybuy.dao.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(
        name = "product_reviews",
        schema = "ecommerce",
        uniqueConstraints = {

                @UniqueConstraint(name = "uq_product_reviews_user_product", columnNames = {"user_id", "product_id"})
        },
        indexes = {

                @Index(name = "idx_product_reviews_product_time", columnList = "product_id, created_at"),

                @Index(name = "idx_product_reviews_user", columnList = "user_id"),

                @Index(name = "idx_product_reviews_active_time", columnList = "is_active, created_at")
        }
)
public class ProductReviewEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;




    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_reviews_user")
    )
    private UserEntity user;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_reviews_product")
    )
    private ProductEntity product;


    @Column(name = "rating", nullable = false)
    private Integer rating;


    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }}
