package com.example.trendybuy.dto.response;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {

    private Long Id;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private BigDecimal amount;
    private String status;
    private String providerRef;
}
