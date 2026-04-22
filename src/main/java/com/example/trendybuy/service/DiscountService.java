package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.DiscountRequest;
import com.example.trendybuy.dto.response.DiscountResponse;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {

    DiscountResponse createDiscount(DiscountRequest request);

    DiscountResponse getDiscountById(Long id);

    List<DiscountResponse> getDiscountsByProductId(Long productId);

    List<DiscountResponse> getActiveDiscounts();

    DiscountResponse updateDiscount(Long id, DiscountRequest request);

    void deleteDiscount(Long id);
    
    // Məhsulun endirimli son qiymətini hesablamaq
    BigDecimal calculateDiscountedPrice(Long productId);
    
    // Endirimləri ləğv etmək
    void expireDiscount(Long id);
}
