// file: src/main/java/com/example/trendybuy/dto/VerifyPhoneOtpRequest.java
package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyPhoneOtpRequest {

    @NotBlank
    private String phone;

    @NotBlank
    private String otp;
}
