// file: src/main/java/com/example/trendybuy/dto/AuthResponse.java
package com.example.trendybuy.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
    private UserDto user;
    private String message;
}
