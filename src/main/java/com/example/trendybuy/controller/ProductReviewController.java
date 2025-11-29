package com.example.trendybuy.controller;

import com.example.trendybuy.dao.entity.ProductReviewEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-reviews")
public class ProductReviewController {


    @GetMapping("/product/{productId}")
    public List<ProductReviewEntity> getReviewsByProductId(@PathVariable Long productId) {
        // TODO: implement
        return null;
    }


    @GetMapping("/{id}")
    public ProductReviewEntity getReviewById(@PathVariable Long id) {
        // TODO: implement
        return null;
    }

    @PostMapping("/product/{productId}")
    public ProductReviewEntity createReview(@PathVariable Long productId, @RequestBody ProductReviewEntity review) {
        // TODO: implement
        return null;
    }


    @PutMapping("/{id}")
    public ProductReviewEntity updateReview(@PathVariable Long id, @RequestBody ProductReviewEntity review) {
        // TODO: implement
        return null;
    }


    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        // TODO: implement
    }

    @GetMapping("/product/{productId}/average-rating")
    public Double getAverageRatingByProductId(@PathVariable Long productId) {
        // TODO: implement
        return null;
    }


    @GetMapping("/product/{productId}/top-reviews")
    public List<ProductReviewEntity> getTopReviewsByProductId(@PathVariable Long productId) {
        // TODO: implement
        return null;
    }
}
//    getReviewsByProductId
//
//getReviewById
//
//createReview

//getReviewsByProductId
//
//getReviewById
//
//createReview
//
//updateReview
//
//deleteReview
//
//getAverageRatingByProductId
//
//getTopReviewsByProductId


