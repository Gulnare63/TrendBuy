package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

    List<ShoppingCartEntity> findByUser_UserId(Long userId);

    Optional<ShoppingCartEntity> findByUser_UserIdAndProduct_Id(Long userId, Long productId);

    void deleteByUser_UserId(Long userId);
}
