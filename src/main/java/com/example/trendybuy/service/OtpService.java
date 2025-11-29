// file: src/main/java/com/example/trendybuy/service/OtpService.java
package com.example.trendybuy.service;

import com.example.trendybuy.enums.OtpType;

public interface OtpService {

    void saveOtp(OtpType type, String identifier, String otp, long ttlSeconds);

    String getOtp(OtpType type, String identifier);

    void deleteOtp(OtpType type, String identifier);
}
