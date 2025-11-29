package com.example.trendybuy.dto.response;

import com.example.trendybuy.enums.SellerStatus;
import lombok.Data;

@Data
public class SellerProfileResponse {

    private Long sellerId;

    private String shopName;

    private String companyName;

    private String taxNumber;

    private String iban;

    private SellerStatus status;

    private String email;

    private String fullName;
}
