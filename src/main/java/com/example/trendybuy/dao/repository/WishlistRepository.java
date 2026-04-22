package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {

    List<WishlistEntity> findByUser_UserId(Long userId);

    Optional<WishlistEntity> findByUser_UserIdAndProduct_Id(Long userId, Long productId);

    void deleteByUser_UserId(Long userId);
}
