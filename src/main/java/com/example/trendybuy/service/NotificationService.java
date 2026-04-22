package com.example.trendybuy.service;

import com.example.trendybuy.dto.response.NotificationResponse;
import com.example.trendybuy.enums.NotificationType;

import java.util.List;

public interface NotificationService {

    void sendNotification(Long userId, String message, NotificationType type);

    List<NotificationResponse> getMyNotifications();

    void markAsRead(Long notificationId);

    void markAllAsRead();
}
