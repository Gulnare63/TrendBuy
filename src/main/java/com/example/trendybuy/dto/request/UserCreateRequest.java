package com.example.trendybuy.dto.request;


import com.example.trendybuy.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    private String fullName;
    private String phoneNumber;

    // admin yaratma endpoint-innde lazım olur
    private UserRole role;


}

