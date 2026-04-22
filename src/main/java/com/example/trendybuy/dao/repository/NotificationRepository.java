package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUser_UserIdOrderByCreatedAtDesc(Long userId);
}
