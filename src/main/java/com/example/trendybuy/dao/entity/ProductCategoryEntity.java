package com.example.trendybuy.dao.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        name = "product_categories",
        schema = "ecommerce",
        uniqueConstraints = {

                @UniqueConstraint(name = "uq_product_categories_name", columnNames = {"category_name"})
        },
        indexes = {

                @Index(name = "idx_product_categories_parent", columnList = "parent_id"),

                @Index(name = "idx_product_categories_active_name", columnList = "is_active, category_name")
        }
)
public class ProductCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;


    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;


    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "parent_id",
            foreignKey = @ForeignKey(name = "fk_product_categories_parent")
    )
    private ProductCategoryEntity parent;


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductEntity> products;


    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<ProductCategoryEntity> children = new ArrayList<>();
}
