// file: src/main/java/com/example/trendybuy/service/impl/OtpServiceImpl.java
package com.example.trendybuy.service.impl;

import com.example.trendybuy.enums.OtpType;
import com.example.trendybuy.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveOtp(OtpType type, String identifier, String otp, long ttlSeconds) {
        String key = buildKey(type, identifier);
        stringRedisTemplate.opsForValue().set(key, otp, ttlSeconds, TimeUnit.SECONDS);
        log.info("Saved OTP for key={} ttlSeconds={}", key, ttlSeconds);
    }

    @Override
    public String getOtp(OtpType type, String identifier) {
        String key = buildKey(type, identifier);
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteOtp(OtpType type, String identifier) {
        String key = buildKey(type, identifier);
        stringRedisTemplate.delete(key);
        log.info("Deleted OTP key={}", key);
    }

    private String buildKey(OtpType type, String identifier) {
        return switch (type) {
            case REGISTER_EMAIL -> "otp:REGISTER:email:" + identifier;
            case REGISTER_PHONE -> "otp:REGISTER:phone:" + identifier;
            case LOGIN -> "otp:LOGIN:" + identifier;
            case PASSWORD_RESET -> "otp:PASSWORD_RESET:" + identifier;
        };
    }
}
