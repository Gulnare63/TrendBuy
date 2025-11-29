package com.example.trendybuy.dao.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "shopping_cart",
        schema = "ecommerce",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_cart_user_product", columnNames = {"user_id", "product_id"})
        }
)
public class ShoppingCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_shopping_cart_user"))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_shopping_cart_product"))
    private ProductEntity product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    @PrePersist
    void prePersist() {
        if (addedAt == null) addedAt = LocalDateTime.now();
    }
}