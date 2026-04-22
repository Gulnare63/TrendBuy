package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

    List<ProductImageEntity> findByProduct_IdOrderBySortOrderAsc(Long productId);

    ProductImageEntity findByProduct_IdAndPrimaryImageTrue(Long productId);
}
