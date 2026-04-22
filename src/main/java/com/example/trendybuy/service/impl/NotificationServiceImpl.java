package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.NotificationEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.NotificationRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.response.NotificationResponse;
import com.example.trendybuy.enums.NotificationType;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.NotificationMapper;
import com.example.trendybuy.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void sendNotification(Long userId, String message, NotificationType type) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            NotificationEntity notification = new NotificationEntity();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setNotificationType(type);
            notification.setRead(false);
            notification.setCreatedAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<NotificationResponse> getMyNotifications() {
        UserEntity currentUser = getCurrentUser();
        List<NotificationEntity> list = notificationRepository.findByUser_UserIdOrderByCreatedAtDesc(currentUser.getUserId());
        return notificationMapper.toResponseList(list);
    }

    @Override
    public void markAsRead(Long notificationId) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR));
        
        UserEntity currentUser = getCurrentUser();
        if (notification.getUser().getUserId().equals(currentUser.getUserId())) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    @Override
    public void markAllAsRead() {
        UserEntity currentUser = getCurrentUser();
        List<NotificationEntity> unreadNotifications = notificationRepository.findByUser_UserIdOrderByCreatedAtDesc(currentUser.getUserId());
        for (NotificationEntity notif : unreadNotifications) {
            if (!notif.isRead()) {
                notif.setRead(true);
            }
        }
        notificationRepository.saveAll(unreadNotifications);
    }

    private UserEntity getCurrentUser() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));
    }
}
