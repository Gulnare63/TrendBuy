package com.example.trendybuy.dto.response;

import lombok.Data;

@Data
public class ShoppingAddressResponse {

    private Long id;
    private String address;
    private String city;
    private String postalCode;
    private boolean defaultAddress;
}
