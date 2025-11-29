// file: src/main/java/com/example/trendybuy/service/impl/JwtServiceImpl.java
package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMillis}")
    private long jwtExpirationMillis;

    @Value("${jwt.refreshExpirationMillis}")
    private long refreshExpirationMillis;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(UserEntity user) {
        return buildToken(user, jwtExpirationMillis);
    }

    @Override
    public String generateRefreshToken(UserEntity user) {
        return buildToken(user, refreshExpirationMillis);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Long extractUserId(String token) {
        Claims claims = parseClaims(token);
        String subject = claims.getSubject();
        return subject != null ? Long.parseLong(subject) : null;
    }

    @Override
    public String extractUsername(String token) {
        Claims claims = parseClaims(token);
        return claims.get("username", String.class);
    }

    private String buildToken(UserEntity user, long expirationMillis) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .claim("username", user.getUserName())
                .claim("role", user.getRole().name())
                .claim("email", user.getEmail())
                .claim("phone", user.getPhoneNumber())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
