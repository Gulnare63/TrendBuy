package com.example.trendybuy.dao.entity;
import com.example.trendybuy.enums.DiscountType;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DiscountType discountType = DiscountType.PERCENTAGE;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountValue; // faiz (10.00%) və ya sabit rəqəm (15.00 AZN)
    
    @Column(precision = 10, scale = 2)
    private BigDecimal minOrderAmount; // Minimum nə qədər alış-veriş olanda bu endirim işləyər
    
    @Column(precision = 10, scale = 2)
    private BigDecimal maxDiscountAmount; // Yalnız faizli endirimlər üçün max endirim məbləği (məs. 50 AZN-ə qədər)

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    private boolean active = true;
    
    @PrePersist
    void prePersist() {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
    }
}
