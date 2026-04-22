package com.example.trendybuy.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateRequest {

    @NotNull(message = "Category ID must not be null")
    private Long categoryId;

    @NotBlank(message = "Product name must not be blank")
    private String name;

    private String sku; // İstəyə bağlı, yoxsa özümüz auto-generate edə bilərik

    private String description;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Stock quantity must not be null")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stockQuantity;
}
