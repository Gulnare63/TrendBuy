package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

    List<HistoryEntity> findByUser_UserIdOrderByChangeTimestampDesc(Long userId);

    List<HistoryEntity> findByOrder_IdOrderByChangeTimestampDesc(Long orderId);
}
