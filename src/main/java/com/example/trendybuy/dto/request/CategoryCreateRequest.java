package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateRequest {

    @NotBlank(message = "Kateqoriya adı boş ola bilməz")
    @Size(min = 2, max = 100, message = "Kateqoriya adı 2-100 simvol arasında olmalıdır")
    private String categoryName;

    // null olsa - ana kateqoriya, dəyər olsa - alt kateqoriya deməkdir
    private Long parentId;
}
