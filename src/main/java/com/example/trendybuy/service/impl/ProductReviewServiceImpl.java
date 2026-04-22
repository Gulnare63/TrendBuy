package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.OrderEntity;
import com.example.trendybuy.dao.entity.OrderItemEntity;
import com.example.trendybuy.dao.entity.ProductEntity;
import com.example.trendybuy.dao.entity.ProductReviewEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.OrderRepository;
import com.example.trendybuy.dao.repository.ProductRepository;
import com.example.trendybuy.dao.repository.ProductReviewRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.request.ProductReviewRequest;
import com.example.trendybuy.dto.response.UserReviewResponse;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.ReviewMapper;
import com.example.trendybuy.service.ProductReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public UserReviewResponse createReview(ProductReviewRequest request) {
        UserEntity currentUser = getCurrentUser();

        // 1. Məhsulun olub olmadığını yoxlayırıq
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));

        // 2. Yalnız bu məhsulu "satın almış" və "çatdırılmış" istifadəçilər rəy yaza bilər
        boolean hasPurchased = false;
        List<OrderEntity> userOrders = orderRepository.findByUser_UserId(currentUser.getUserId());
        for (OrderEntity order : userOrders) {
            // Məsələn, sadəcə DELIVERED statuslu sifarişdə olduqda rəy yaza bilər (şərti olaraq bütün sifarişlərə icazə verirəm, amma gələcəkdə bu dəyişə bilər)
            for (OrderItemEntity item : order.getOrderitems()) {
                if (item.getProduct().getId().equals(product.getId())) {
                    hasPurchased = true;
                    break;
                }
            }
        }

        if (!hasPurchased) {
            throw new IllegalArgumentException("You can only review products you have purchased.");
        }

        ProductReviewEntity review = new ProductReviewEntity();
        review.setUser(currentUser);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());
        review.setActive(false); // Admin təsdiqləyənə qədər false ola bilər. Amma indilik saytda dərhal görünməsi üçün true edək.
        review.setActive(true);

        ProductReviewEntity saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<UserReviewResponse> getProductReviews(Long productId) {
        return reviewMapper.toResponseList(reviewRepository.findByProduct_IdAndActiveTrue(productId));
    }

    @Override
    public void approveReview(Long id) {
        // Admin funksiyası
        ProductReviewEntity review = reviewRepository.findById(id).orElseThrow();
        review.setActive(true);
        reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        // Admin və ya öz rəyi silmək
        ProductReviewEntity review = reviewRepository.findById(id).orElseThrow();
        review.setActive(false);
        reviewRepository.save(review);
    }

    private UserEntity getCurrentUser() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));
    }
}
