package com.example.trendybuy.dao.entity;

import com.example.trendybuy.enums.HistoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "history",
        schema = "ecommerce",
        indexes = {
                @Index(name = "idx_history_order_time", columnList = "order_id, change_timestamp"),

                @Index(name = "idx_history_user", columnList = "user_id"),

                @Index(name = "idx_history_payment", columnList = "payment_id")
        }
)
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_history_user")
    )
    private UserEntity user;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "order_id",
            foreignKey = @ForeignKey(name = "fk_history_order")
    )
    private OrderEntity order;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "payment_id",
            foreignKey = @ForeignKey(name = "fk_history_payment")
    )
    private PaymentEntity payment;


    @Column(nullable = false, length = 255)
    private String changeDescription;


    @Column(nullable = false)
    private LocalDateTime changeTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "history_type", nullable = false, length = 50)
    private HistoryType historyType;


    @PrePersist
    void prePersist() {
        if (changeTimestamp == null) {
            changeTimestamp = LocalDateTime.now();
        }
    }
}

