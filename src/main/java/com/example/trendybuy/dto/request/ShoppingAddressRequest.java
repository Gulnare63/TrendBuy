package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShoppingAddressRequest {

    @NotBlank(message = "Address cannot be blank")
    private String address;

    private String city;

    private String postalCode;

    private boolean defaultAddress;
}
