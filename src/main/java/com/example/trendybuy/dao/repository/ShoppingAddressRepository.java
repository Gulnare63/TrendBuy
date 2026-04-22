package com.example.trendybuy.dao.repository;

import com.example.trendybuy.dao.entity.ShoppingAddresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingAddressRepository extends JpaRepository<ShoppingAddresEntity, Long> {

    List<ShoppingAddresEntity> findByUser_UserId(Long userId);

    Optional<ShoppingAddresEntity> findByUser_UserIdAndDefaultAddressTrue(Long userId);
}
