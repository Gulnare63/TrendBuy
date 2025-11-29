package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.SellerProfileEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerProfileRepository extends JpaRepository<SellerProfileEntity, Long> {

    Optional<SellerProfileEntity> findByUser(UserEntity user);
}
