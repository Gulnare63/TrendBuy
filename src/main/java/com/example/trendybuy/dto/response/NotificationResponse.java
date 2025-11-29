package com.example.trendybuy.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {

    private Long id;
    private String message;
    private String notificationType;
    private boolean read;
    private LocalDateTime createdAt;
}

