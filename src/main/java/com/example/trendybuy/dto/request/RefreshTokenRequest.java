// file: src/main/java/com/example/trendybuy/dto/RefreshTokenRequest.java
package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotBlank
    private String refreshToken;
}
