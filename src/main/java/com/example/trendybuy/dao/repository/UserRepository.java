package com.example.trendybuy.dao.repository;


import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    List<UserEntity> findAllByRole(UserRole role);

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

}
