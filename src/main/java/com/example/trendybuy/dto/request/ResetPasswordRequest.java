// file: src/main/java/com/example/trendybuy/dto/ResetPasswordRequest.java
package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank
    private String identifier; // email və ya phone

    @NotBlank
    private String otp;

    @NotBlank
    @Size(min = 6, max = 100)
    private String newPassword;
}
