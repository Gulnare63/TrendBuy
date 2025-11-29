// file: src/main/java/com/example/trendybuy/dto/LoginRequest.java
package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    // email və ya phone
    @NotBlank
    private String identifier;

    @NotBlank
    private String password;
}
