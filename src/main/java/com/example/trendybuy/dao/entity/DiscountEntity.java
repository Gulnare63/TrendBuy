package com.example.trendybuy.dao.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "discounts", schema = "ecommerce")
public class DiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_discounts_product"))
    private ProductEntity product;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercent;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    private boolean active = true;
}
