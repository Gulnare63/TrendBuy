package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.ProductImageRequest;
import com.example.trendybuy.dto.response.ProductImageResponse;

import java.util.List;

public interface ProductImagesService {

    ProductImageResponse addImage(ProductImageRequest request);

    List<ProductImageResponse> getProductImages(Long productId);

    void deleteImage(Long id);

    void setPrimaryImage(Long imageId);
    
    // Sort etmək üçün, gələcəkdə istifadə edə bilərik (məsələn drag and drop)
    // void updateSortOrder(Long imageId, Integer newOrder); 
}
