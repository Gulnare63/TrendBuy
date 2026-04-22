package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.ProductCategoryEntity;
import com.example.trendybuy.dao.entity.ProductEntity;
import com.example.trendybuy.dao.entity.SellerProfileEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.ProductCategoryRepository;
import com.example.trendybuy.dao.repository.ProductRepository;
import com.example.trendybuy.dao.repository.SellerProfileRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.request.ProductCreateRequest;
import com.example.trendybuy.dto.request.ProductUpdateRequest;
import com.example.trendybuy.dto.response.ProductResponse;
import com.example.trendybuy.enums.SellerStatus;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.ProductMapper;
import com.example.trendybuy.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SellerProfileRepository sellerRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        // Yalnız aktiv sellerlər məhsul yükləyə bilər
        SellerProfileEntity currentSeller = getCurrentActiveSeller();

        ProductCategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.CATEGORY_NOT_FOUND));

        ProductEntity entity = productMapper.toEntity(request);
        entity.setSeller(currentSeller);
        entity.setCategory(category);
        // SKU daxil edilməyibsə adından və vaxtdan istifadə edərək düzəldək
        if (request.getSku() == null || request.getSku().isBlank()) {
            entity.setSku(generateSku(request.getName()));
        }

        ProductEntity saved = productRepository.save(entity);
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public ProductResponse getProductById(Long id) {
        ProductEntity entity = findProduct(id);
        return productMapper.toResponse(entity);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductResponse> getAllProducts() {
        return productMapper.toResponseList(productRepository.findByActiveTrue());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        SellerProfileEntity currentSeller = getCurrentActiveSeller();
        ProductEntity entity = findProduct(id);

        // Satıcı doğrulamasını (bu məhsul doğrudanmı bu user-ə aiddir)
        verifyProductOwnership(entity, currentSeller);

        productMapper.updateEntityFromRequest(request, entity);

        if (request.getCategoryId() != null && !request.getCategoryId().equals(entity.getCategory().getId())) {
            ProductCategoryEntity category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.CATEGORY_NOT_FOUND));
            entity.setCategory(category);
        }

        ProductEntity updated = productRepository.save(entity);
        return productMapper.toResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        SellerProfileEntity currentSeller = getCurrentActiveSeller();
        ProductEntity entity = findProduct(id);

        verifyProductOwnership(entity, currentSeller);

        // Tam silmək (hard-delete) yerinə statusunu false etmək daha təhlükəsizdir
        entity.setActive(false);
        productRepository.save(entity);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        // Kateqoriya ID-yə görə (yalnız aktiv məhsullar)
        return productMapper.toResponseList(productRepository.findByCategory_IdAndActiveTrue(categoryId));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductResponse> searchProducts(String name, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice) {
        return productMapper.toResponseList(productRepository.searchProducts(name, categoryId, minPrice, maxPrice));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductResponse> getTopRatedProducts() {
        // Hələ ki sadəcə bütün aktiv məhsulları qaytarır.
        // ProductReview modulu hazırlandıqda bu məntiq dəyişiləcək (join və AVG(rating) olacaq)
        return productMapper.toResponseList(productRepository.findByActiveTrue());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductResponse> getProductsOnDiscount() {
        return productMapper.toResponseList(productRepository.findDiscountedProducts());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductResponse> getMyProducts() {
        SellerProfileEntity currentSeller = getCurrentActiveSeller();
        return productMapper.toResponseList(productRepository.findBySeller_SellerId(currentSeller.getSellerId()));
    }

    @Override
    public void activateProduct(Long id) {
        SellerProfileEntity currentSeller = getCurrentActiveSeller();
        ProductEntity entity = findProduct(id);
        verifyProductOwnership(entity, currentSeller);
        
        entity.setActive(true);
        productRepository.save(entity);
    }

    @Override
    public void deactivateProduct(Long id) {
        SellerProfileEntity currentSeller = getCurrentActiveSeller();
        ProductEntity entity = findProduct(id);
        verifyProductOwnership(entity, currentSeller);
        
        entity.setActive(false);
        productRepository.save(entity);
    }

    // ============================ Helper Metodlar ============================

    private ProductEntity findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));
    }

    private SellerProfileEntity getCurrentActiveSeller() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));

        SellerProfileEntity seller = sellerRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.SELLER_NOT_FOUND));

        if (seller.getStatus() != SellerStatus.ACTIVE) {
            throw new NotFoundException(ExceptionCode.ACCOUNT_NOT_ACTIVE);
        }

        return seller;
    }

    private void verifyProductOwnership(ProductEntity product, SellerProfileEntity currentSeller) {
        if (!product.getSeller().getSellerId().equals(currentSeller.getSellerId())) {
            throw new IllegalArgumentException("You are not authorized to modify this product"); // və ya yeni ForbiddenException 
        }
    }

    private String generateSku(String productName) {
        // Sadə SKU Generator, irəlidə daha da inkişaf etdirilə bilər
        String base = productName.substring(0, Math.min(productName.length(), 3)).toUpperCase();
        long random = (long) (Math.random() * 1000000);
        return base + "-" + random;
    }
}
