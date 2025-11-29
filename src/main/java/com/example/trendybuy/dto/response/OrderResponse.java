package com.example.trendybuy.dto.response;


import com.example.trendybuy.enums.OrderStatus;
import com.example.trendybuy.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private String shippingAddressText;
    private String shippingCity;
    private String shippingPostalCode;
    private String notes;
    private LocalDateTime updatedAt;


    private List<OrderItemResponse> items;
    private List<PaymentResponse> payments;
}

