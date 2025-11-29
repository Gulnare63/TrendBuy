package com.example.trendybuy.dto.response;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long orderItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal lineTotal; // quantity * price
}

