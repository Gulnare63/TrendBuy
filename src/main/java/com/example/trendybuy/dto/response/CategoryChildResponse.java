package com.example.trendybuy.dto.response;

import lombok.Builder;
import lombok.Data;

// Sonsuz rekursiyadan qaçmaq üçün alt-kateqoriyaları sadə formada qaytarırıq
@Data
@Builder
public class CategoryChildResponse {

    private Long id;
    private String categoryName;
    private boolean active;
}
