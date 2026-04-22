package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {

    List<DiscountEntity> findByProduct_IdAndActiveTrue(Long productId);

    List<DiscountEntity> findByActiveTrue();
}
