package com.example.trendybuy.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressResponse {

    private Long id;
    private String address;
    private String city;
    private String postalCode;
    private boolean defaultAddress;
    private LocalDateTime createdAt;
}
