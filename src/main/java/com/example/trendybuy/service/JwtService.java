// file: src/main/java/com/example/trendybuy/service/JwtService.java
package com.example.trendybuy.service;

import com.example.trendybuy.dao.entity.UserEntity;

public interface JwtService {

    String generateAccessToken(UserEntity user);

    String generateRefreshToken(UserEntity user);

    boolean isTokenValid(String token);

    Long extractUserId(String token);

    String extractUsername(String token);
}
