package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryUpdateRequest {

    @Size(min = 2, max = 100, message = "Kateqoriya adı 2-100 simvol arasında olmalıdır")
    private String categoryName;

    // null göndərilsə dəyişmir
    private Long parentId;
}
