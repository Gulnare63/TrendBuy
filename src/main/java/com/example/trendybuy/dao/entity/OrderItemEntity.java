package com.example.trendybuy.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(
        name = "order_items",
        schema = "ecommerce",
        uniqueConstraints = {

                @UniqueConstraint(name = "uq_order_items_order_product", columnNames = {"order_id", "product_id"})
        },
        indexes = {

                @Index(name = "idx_order_items_order", columnList = "order_id"),

                @Index(name = "idx_order_items_product", columnList = "product_id")
        }
)
@Getter
@Setter
public class OrderItemEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_items_order")
    )
    private OrderEntity order;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_items_product")
    )
    private ProductEntity product;


    @Column(nullable = false)
    private Integer quantity;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
}