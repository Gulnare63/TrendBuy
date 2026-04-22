package com.example.trendybuy.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WishlistResponse {

    private Long wishlistId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Boolean productActive;
    private LocalDateTime addedAt;
}
