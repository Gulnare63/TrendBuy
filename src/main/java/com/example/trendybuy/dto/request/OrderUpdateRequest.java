package com.example.trendybuy.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateRequest {

    private String shippingAddressText;
    private String shippingCity;
    private String shippingPostalCode;
    private String notes;


}

