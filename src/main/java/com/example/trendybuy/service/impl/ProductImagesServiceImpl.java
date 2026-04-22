package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.ProductEntity;
import com.example.trendybuy.dao.entity.ProductImageEntity;
import com.example.trendybuy.dao.repository.ProductImageRepository;
import com.example.trendybuy.dao.repository.ProductRepository;
import com.example.trendybuy.dto.request.ProductImageRequest;
import com.example.trendybuy.dto.response.ProductImageResponse;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.ProductImageMapper;
import com.example.trendybuy.service.ProductImagesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImagesServiceImpl implements ProductImagesService {

    private final ProductImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ProductImageMapper mapper;

    @Override
    public ProductImageResponse addImage(ProductImageRequest request) {
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));

        // İlk şəkildirsə, məcbur əsas şəkil (primary) edirik
        List<ProductImageEntity> existingImages = imageRepository.findByProduct_IdOrderBySortOrderAsc(product.getId());
        boolean isPrimary = request.isPrimaryImage();
        if (existingImages.isEmpty()) {
            isPrimary = true;
        }

        if (isPrimary) {
            clearPrimary(product.getId());
        }

        ProductImageEntity entity = new ProductImageEntity();
        entity.setProduct(product);
        entity.setImageUrl(request.getImageUrl());
        entity.setPrimaryImage(isPrimary);
        
        int sortOrder = request.getSortOrder() != null ? request.getSortOrder() : existingImages.size() + 1;
        entity.setSortOrder(sortOrder);

        return mapper.toResponse(imageRepository.save(entity));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductImageResponse> getProductImages(Long productId) {
        return mapper.toResponseList(imageRepository.findByProduct_IdOrderBySortOrderAsc(productId));
    }

    @Override
    public void deleteImage(Long id) {
        ProductImageEntity image = imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR)); 
        
        Long productId = image.getProduct().getId();
        boolean wasPrimary = image.isPrimaryImage();
        
        imageRepository.delete(image);

        // Əgər əsas şəkli sildisə və fərqli şəkillər varsa, ilkini avtomatik əsas etmək
        if (wasPrimary) {
            List<ProductImageEntity> remaining = imageRepository.findByProduct_IdOrderBySortOrderAsc(productId);
            if (!remaining.isEmpty()) {
                ProductImageEntity newPrimary = remaining.get(0);
                newPrimary.setPrimaryImage(true);
                imageRepository.save(newPrimary);
            }
        }
    }

    @Override
    public void setPrimaryImage(Long imageId) {
        ProductImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR)); 
                
        clearPrimary(image.getProduct().getId());
        image.setPrimaryImage(true);
        imageRepository.save(image);
    }

    private void clearPrimary(Long productId) {
        ProductImageEntity primary = imageRepository.findByProduct_IdAndPrimaryImageTrue(productId);
        if (primary != null) {
            primary.setPrimaryImage(false);
            imageRepository.save(primary);
        }
    }
}
