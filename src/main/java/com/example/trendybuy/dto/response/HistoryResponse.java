package com.example.trendybuy.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryResponse {

    private Long id;
    private Long userId;
    private Long orderId;
    private Long paymentId;
    private String description;
    private String type;
    private LocalDateTime timestamp;
}
