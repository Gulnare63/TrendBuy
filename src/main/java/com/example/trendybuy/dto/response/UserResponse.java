package com.example.trendybuy.dto.response;


import com.example.trendybuy.enums.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserResponse {

    private Long id;
    private String userName;
    private String email;
    private String fullName;
    private String phoneNumber;
    private UserRole role;
    private boolean active;
    private LocalDateTime registrationDate;


//    burada orders, notifications, addresses və s. YOXDUR. Onlar ayrı endpoint-lərlə veriləcə
}
