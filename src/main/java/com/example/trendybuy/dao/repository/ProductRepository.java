package com.example.trendybuy.dao.repository;


import com.example.trendybuy.dao.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByCategory_IdAndActiveTrue(Long categoryId);
    
    List<ProductEntity> findBySeller_SellerId(Long sellerId);
    
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"category", "seller"})
    List<ProductEntity> findByActiveTrue();

    @Query("SELECT p FROM ProductEntity p WHERE " +
           "p.active = true AND " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {"category", "seller"})
    List<ProductEntity> searchProducts(
            @Param("name") String name,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice
    );
    

    @Query("SELECT p FROM ProductEntity p JOIN p.discount d WHERE d.active = true AND d.startDate <= CURRENT_TIMESTAMP AND d.endDate >= CURRENT_TIMESTAMP")
    List<ProductEntity> findDiscountedProducts();
}
