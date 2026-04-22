package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.DiscountRequest;
import com.example.trendybuy.dto.response.DiscountResponse;
import com.example.trendybuy.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping("/product/{productId}")
    public List<DiscountResponse> getDiscountsByProductId(@PathVariable Long productId) {
        return discountService.getDiscountsByProductId(productId);
    }

    @GetMapping("/{id}")
    public DiscountResponse getDiscountById(@PathVariable Long id) {
        return discountService.getDiscountById(id);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public DiscountResponse createDiscount(@Valid @RequestBody DiscountRequest request) {
        return discountService.createDiscount(request);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/{id}")
    public DiscountResponse updateDiscount(@PathVariable Long id, @Valid @RequestBody DiscountRequest request) {
        return discountService.updateDiscount(id, request);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public void deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
    }

    @GetMapping("/active")
    public List<DiscountResponse> getActiveDiscounts() {
        return discountService.getActiveDiscounts();
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/{id}/expire")
    public void expireDiscount(@PathVariable Long id) {
        discountService.expireDiscount(id);
    }

    @GetMapping("/product/{productId}/calculate")
    public BigDecimal calculateDiscountedPrice(@PathVariable Long productId) {
        return discountService.calculateDiscountedPrice(productId);
    }
}
//getDiscountsByProductId
//
//getDiscountById
//
//createDiscount
//
//updateDiscount
//
//deleteDiscount
//
//getActiveDiscounts
//
//applyDiscountToProduct
//
//expireDiscount
//
//calculateDiscountedPrice

