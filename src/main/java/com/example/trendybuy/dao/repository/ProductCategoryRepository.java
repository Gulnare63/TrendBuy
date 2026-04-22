package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    boolean existsByCategoryName(String categoryName);

    Optional<ProductCategoryEntity> findByCategoryName(String categoryName);

    // Yalnız ana kateqoriyalar (parent-i olmayan)
    List<ProductCategoryEntity> findAllByParentIsNull();

    // Müəyyən parent-ə aid alt-kateqoriyalar
    List<ProductCategoryEntity> findAllByParentId(Long parentId);

    // Aktiv kateqoriyalar + parent yoxdur
    List<ProductCategoryEntity> findAllByActiveTrueAndParentIsNull();

    // Alt-kateqoriyanın mövcud olub-olmadığını yoxla (silinmədən öncə)
    boolean existsByParentId(Long parentId);

    // Bir kateqoriyanın alt kateqoriyaları aktiv olanlar
    @Query("SELECT c FROM ProductCategoryEntity c WHERE c.parent.id = :parentId AND c.active = true")
    List<ProductCategoryEntity> findActiveChildrenByParentId(Long parentId);

    // Kateqoriyaya aid məhsul var mı?
    @Query("SELECT COUNT(p) > 0 FROM ProductEntity p WHERE p.category.id = :categoryId")
    boolean hasProducts(Long categoryId);
}
