// file: src/main/java/com/example/trendybuy/dto/VerifyLoginOtpRequest.java
package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyLoginOtpRequest {

    @NotBlank
    private String identifier;  // email və ya phone

    @NotBlank
    private String otp;
}
