package com.example.trendybuy.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequestt {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;


}

