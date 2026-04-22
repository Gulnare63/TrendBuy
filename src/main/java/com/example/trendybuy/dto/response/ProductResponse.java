package com.example.trendybuy.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponse {

    private Long id;
    
    // Seller məlumatları (sadə)
    private Long sellerId;
    private String sellerShopName;
    
    // Kateqoriya məlumatları
    private Long categoryId;
    private String categoryName;

    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private boolean active;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
