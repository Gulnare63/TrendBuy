package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByOrder_id(Long orderId);
}