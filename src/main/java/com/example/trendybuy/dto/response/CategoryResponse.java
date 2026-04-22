package com.example.trendybuy.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryResponse {

    private Long id;
    private String categoryName;
    private boolean active;

    // Ana kateqoriyanın id və adı (null ola bilər - əgər özü ana kateqoriyadırsa)
    private Long parentId;
    private String parentName;

    // Bu kateqoriyaya aid alt-kateqoriyaların sadə siyahısı
    private List<CategoryChildResponse> children;

    // Kateqoriyadakı məhsul sayı
    private int productCount;
}
