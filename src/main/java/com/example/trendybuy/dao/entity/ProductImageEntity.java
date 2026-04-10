package com.example.trendybuy.dao.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
        name = "product_images",
        schema = "ecommerce",
        uniqueConstraints = {

                @UniqueConstraint(name = "uq_product_images_product_url", columnNames = {"product_id", "image_url"}),

                @UniqueConstraint(name = "uq_product_images_product_sort", columnNames = {"product_id", "sort_order"})
        },
        indexes = {

                @Index(name = "idx_product_images_product_sort", columnList = "product_id, sort_order")

        }
)
public class ProductImageEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_images_product")
    )
    private ProductEntity product;


    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;


    @Column(name = "is_primary", nullable = false)
    private boolean primaryImage = false;


    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
}

