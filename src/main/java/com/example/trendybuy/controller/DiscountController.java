package com.example.trendybuy.controller;

import com.example.trendybuy.dao.entity.DiscountEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {


    @GetMapping("/product/{productId}")
    public List<DiscountEntity> getDiscountsByProductId(@PathVariable Long productId) {
        // TODO: implement
        return null;
    }


    @GetMapping("/{id}")
    public DiscountEntity getDiscountById(@PathVariable Long id) {
        // TODO: implement
        return null;
    }


    @PostMapping
    public DiscountEntity createDiscount(@RequestBody DiscountEntity discount) {
        // TODO: implement
        return null;
    }

    @PutMapping("/{id}")
    public DiscountEntity updateDiscount(@PathVariable Long id, @RequestBody DiscountEntity discount) {
        // TODO: implement
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteDiscount(@PathVariable Long id) {
        // TODO: implement
    }


    @GetMapping("/active")
    public List<DiscountEntity> getActiveDiscounts() {
        // TODO: implement
        return null;
    }


    @PostMapping("/{id}/apply")
    public void applyDiscountToProduct(@PathVariable Long id) {
        // TODO: implement
    }


    @PostMapping("/{id}/expire")
    public void expireDiscount(@PathVariable Long id) {
        // TODO: implement
    }


    @GetMapping("/product/{productId}/calculate")
    public Double calculateDiscountedPrice(@PathVariable Long productId) {
        // TODO: implement
        return null;
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

