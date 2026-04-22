package com.example.trendybuy.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class OrderCreateRequest {

    private List<OrderItemCreateRequest> items;

    private String shippingAddressText;
    private String shippingCity;
    private String shippingPostalCode;
    private String notes;

    // admin paneldən yaratsaq eger,
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;


}

