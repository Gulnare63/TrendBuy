package com.example.trendybuy.dao.entity;
import com.example.trendybuy.enums.PaymentMethod;
import com.example.trendybuy.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "payments",
        schema = "ecommerce",
        indexes = {
                @Index(name = "idx_payments_order", columnList = "order_id"),
                @Index(name = "idx_payments_status_date", columnList = "status, payment_date")
        }
)
public class PaymentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payments_order")
    )
    private OrderEntity order;


    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 50)
    private PaymentMethod paymentMethod;


    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private PaymentStatus status;


    @Column(name = "provider_ref", length = 100)
    private String providerRef;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HistoryEntity> history;

    @PrePersist
    void prePersist() {
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }}
