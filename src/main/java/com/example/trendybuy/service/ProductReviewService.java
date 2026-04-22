package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.ProductReviewRequest;
import com.example.trendybuy.dto.response.UserReviewResponse;

import java.util.List;

public interface ProductReviewService {

    UserReviewResponse createReview(ProductReviewRequest request);

    List<UserReviewResponse> getProductReviews(Long productId);

    void approveReview(Long id);

    void deleteReview(Long id);
}
