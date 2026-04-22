package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.DiscountEntity;
import com.example.trendybuy.dao.entity.ProductEntity;
import com.example.trendybuy.dao.repository.DiscountRepository;
import com.example.trendybuy.dao.repository.ProductRepository;
import com.example.trendybuy.dto.request.DiscountRequest;
import com.example.trendybuy.dto.response.DiscountResponse;
import com.example.trendybuy.enums.DiscountType;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.DiscountMapper;
import com.example.trendybuy.service.DiscountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final DiscountMapper discountMapper;

    @Override
    public DiscountResponse createDiscount(DiscountRequest request) {
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));

        DiscountEntity entity = discountMapper.toEntity(request);
        entity.setProduct(product);
        
        DiscountEntity saved = discountRepository.save(entity);
        return discountMapper.toResponse(saved);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public DiscountResponse getDiscountById(Long id) {
        DiscountEntity entity = findDiscount(id);
        return discountMapper.toResponse(entity);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DiscountResponse> getDiscountsByProductId(Long productId) {
        return discountMapper.toResponseList(discountRepository.findByProduct_IdAndActiveTrue(productId));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DiscountResponse> getActiveDiscounts() {
        return discountMapper.toResponseList(discountRepository.findByActiveTrue());
    }

    @Override
    public DiscountResponse updateDiscount(Long id, DiscountRequest request) {
        DiscountEntity entity = findDiscount(id);
        
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));
                
        entity.setProduct(product);
        entity.setDiscountType(request.getDiscountType());
        entity.setDiscountValue(request.getDiscountValue());
        entity.setMinOrderAmount(request.getMinOrderAmount());
        entity.setMaxDiscountAmount(request.getMaxDiscountAmount());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        
        DiscountEntity updated = discountRepository.save(entity);
        return discountMapper.toResponse(updated);
    }

    @Override
    public void deleteDiscount(Long id) {
        DiscountEntity entity = findDiscount(id);
        entity.setActive(false);
        discountRepository.save(entity);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public BigDecimal calculateDiscountedPrice(Long productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));
                
        List<DiscountEntity> discounts = discountRepository.findByProduct_IdAndActiveTrue(productId);
        
        BigDecimal originalPrice = product.getPrice();
        BigDecimal finalPrice = originalPrice;
        
        // Sadəcə ən çox endirim edən kampaniyanı seçmək məntiqi
        BigDecimal maxDiscountAmount = BigDecimal.ZERO;
        
        for (DiscountEntity discount : discounts) {
            if (discount.getEndDate() != null && discount.getEndDate().isBefore(LocalDateTime.now())) {
                 continue; // Vaxti kecib
            }
            if (discount.getStartDate() != null && discount.getStartDate().isAfter(LocalDateTime.now())) {
                 continue; // Hele baslamayib
            }
            
            BigDecimal currentDiscountAmt = BigDecimal.ZERO;
            
            if (discount.getDiscountType() == DiscountType.PERCENTAGE) {
                currentDiscountAmt = originalPrice.multiply(discount.getDiscountValue()).divide(BigDecimal.valueOf(100));
                if (discount.getMaxDiscountAmount() != null && currentDiscountAmt.compareTo(discount.getMaxDiscountAmount()) > 0) {
                    currentDiscountAmt = discount.getMaxDiscountAmount();
                }
            } else if (discount.getDiscountType() == DiscountType.FIXED_AMOUNT) {
                currentDiscountAmt = discount.getDiscountValue();
            }
            
            if (currentDiscountAmt.compareTo(maxDiscountAmount) > 0) {
                maxDiscountAmount = currentDiscountAmt;
            }
        }
        
        finalPrice = originalPrice.subtract(maxDiscountAmount);
        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
            finalPrice = BigDecimal.ZERO; // Pulsuz ola biler
        }
        
        return finalPrice;
    }

    @Override
    public void expireDiscount(Long id) {
        DiscountEntity entity = findDiscount(id);
        entity.setActive(false);
        entity.setEndDate(LocalDateTime.now());
        discountRepository.save(entity);
    }

    // ================== Helper ==================
    private DiscountEntity findDiscount(Long id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR)); 
    }
}
