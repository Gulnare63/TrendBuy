package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.ProductCreateRequest;
import com.example.trendybuy.dto.request.ProductUpdateRequest;
import com.example.trendybuy.dto.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, ProductUpdateRequest request);

    void deleteProduct(Long id);

    List<ProductResponse> getProductsByCategory(Long categoryId);

    List<ProductResponse> searchProducts(String name, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice);

    List<ProductResponse> getTopRatedProducts();

    List<ProductResponse> getProductsOnDiscount();
    
    // Sellerin öz məhsullarını görməsi üçün
    List<ProductResponse> getMyProducts();
    
    // Yalnız məhsulu aktivləşdirmək və deaktiv etmək üçün xüsusi metodlar
    void activateProduct(Long id);
    void deactivateProduct(Long id);
}
