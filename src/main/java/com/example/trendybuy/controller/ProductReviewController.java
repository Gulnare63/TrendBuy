package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.ProductReviewRequest;
import com.example.trendybuy.dto.response.UserReviewResponse;
import com.example.trendybuy.service.ProductReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService reviewService;

    @PostMapping
    public UserReviewResponse createReview(@Valid @RequestBody ProductReviewRequest request) {
        return reviewService.createReview(request);
    }

    @GetMapping("/product/{productId}")
    public List<UserReviewResponse> getProductReviews(@PathVariable Long productId) {
        return reviewService.getProductReviews(productId);
    }
}
