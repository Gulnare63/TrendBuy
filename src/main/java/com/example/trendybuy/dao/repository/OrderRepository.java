package com.example.trendybuy.dao.repository;


import com.example.trendybuy.dao.entity.OrderEntity;
import com.example.trendybuy.enums.OrderStatus;
import com.example.trendybuy.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser_UserId(Long userId);

    List<OrderEntity> findByStatus(OrderStatus status);

    List<OrderEntity> findByPaymentStatus(PaymentStatus paymentStatus);
}

