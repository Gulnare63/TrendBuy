package com.example.trendybuy.dto.request;


import com.example.trendybuy.enums.UserRole;
import lombok.Data;

@Data
public class UserUpdateRequest {

    private String fullName;
    private String phoneNumber;
    private UserRole role;     // yalnız admin istifadə edər
    private Boolean active;    // yalnız admin istifadə edər


}
