package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.SellerProfileEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.enums.SellerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerProfileRepository extends JpaRepository<SellerProfileEntity, Long> {

    Optional<SellerProfileEntity> findByUser(UserEntity user);
    List<SellerProfileEntity> findByStatus(SellerStatus status);
}
