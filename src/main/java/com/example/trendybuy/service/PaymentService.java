package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.PaymentRequest;
import com.example.trendybuy.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getPaymentsByOrderId(Long orderId);
}
