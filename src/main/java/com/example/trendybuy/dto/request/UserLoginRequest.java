package com.example.trendybuy.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;


}
