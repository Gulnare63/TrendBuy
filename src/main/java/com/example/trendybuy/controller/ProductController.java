package com.example.trendybuy.controller;

import com.example.trendybuy.dao.entity.ProductEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public
    List<ProductEntity> getAllProducts() {
        // TODO: implement
        return null;
    }

    @GetMapping("/{id}")
    public ProductEntity getProductById(@PathVariable Long id) {
        // TODO: implement
        return null;
    }


    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        // TODO: implement
        return null;
    }


    @PutMapping("/{id}")
    public ProductEntity updateProduct(@PathVariable Long id, @RequestBody ProductEntity product) {
        // TODO: implement
        return null;
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        // TODO: implement
    }


    @GetMapping("/search")
    public List<ProductEntity> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        // TODO: implement
        return null;
    }


    @GetMapping("/top-rated")
    public List<ProductEntity> getTopRatedProducts() {
        // TODO: implement
        return null;
    }


    @GetMapping("/on-discount")
    public List<ProductEntity> getProductsOnDiscount() {
        // TODO: implement
        return null;
    }


    @GetMapping("/by-category/{categoryId}")
    public List<ProductEntity> getProductsByCategory(@PathVariable Long categoryId) {
        // TODO: implement
        return null;
    }
//    getProductById
//
//createProduct
//
//updateProduct
//
//deleteProduct
//
//getProductsByCategoryId
//
//searchProducts (opsional: filter price, stock, active)
}
