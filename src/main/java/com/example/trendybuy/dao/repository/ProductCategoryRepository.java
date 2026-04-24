package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    boolean existsByCategoryName(String categoryName);

    Optional<ProductCategoryEntity> findByCategoryName(String categoryName);

    List<ProductCategoryEntity> findAllByParentIsNull();

    List<ProductCategoryEntity> findAllByParentId(Long parentId);

    List<ProductCategoryEntity> findAllByActiveTrueAndParentIsNull();

    boolean existsByParentId(Long parentId);

    @Query("SELECT c FROM ProductCategoryEntity c WHERE c.parent.id = :parentId AND c.active = true")
    List<ProductCategoryEntity> findActiveChildrenByParentId(Long parentId);

    @Query("SELECT COUNT(p) > 0 FROM ProductEntity p WHERE p.category.id = :categoryId")
    boolean hasProducts(Long categoryId);
}
