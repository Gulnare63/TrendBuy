package com.example.trendybuy.dto.request;


import com.example.trendybuy.enums.OrderStatus;
import com.example.trendybuy.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class OrderStatusUpdateRequest {

    private OrderStatus status;
    private PaymentStatus paymentStatus;


}
