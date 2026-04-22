package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.ProductReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReviewEntity, Long> {

    List<ProductReviewEntity> findByProduct_IdAndActiveTrue(Long productId);

    List<ProductReviewEntity> findByUser_UserId(Long userId);
}
