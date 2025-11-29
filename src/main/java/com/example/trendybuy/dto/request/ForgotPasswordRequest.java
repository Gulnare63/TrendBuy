// file: src/main/java/com/example/trendybuy/dto/ForgotPasswordRequest.java
package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @NotBlank
    private String identifier; // email və ya phone
}
