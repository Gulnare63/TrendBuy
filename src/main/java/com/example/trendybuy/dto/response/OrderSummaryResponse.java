package com.example.trendybuy.dto.response;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderSummaryResponse {

    private Long orderId;
    private LocalDateTime orderDate;
    private String status;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private String shippingCity;
}
