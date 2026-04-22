package com.example.trendybuy.dto.response;

import lombok.Data;

@Data
public class ProductImageResponse {

    private Long id;
    private Long productId;
    private String imageUrl;
    private boolean primaryImage;
    private Integer sortOrder;
}
