package com.example.trendybuy.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserReviewResponse {

    private Long Id;
    private Long productId;
    private String productName;
    private int rating;
    private String comment;
    private boolean active;
    private LocalDateTime createdAt;
}

