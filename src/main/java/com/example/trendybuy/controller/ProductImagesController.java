package com.example.trendybuy.controller;

import com.example.trendybuy.dao.entity.ProductImageEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
public class ProductImagesController {


    @GetMapping("/product/{productId}")
    public List<ProductImageEntity> getImagesByProductId(@PathVariable Long productId) {
        // TODO: implement
        return null;
    }


    @GetMapping("/{id}")
    public ProductImageEntity getImageById(@PathVariable Long id) {
        // TODO: implement
        return null;
    }


    @PostMapping("/product/{productId}")
    public ProductImageEntity addImageToProduct(@PathVariable Long productId, @RequestBody ProductImageEntity image) {
        // TODO: implement
        return null;
    }


    @PutMapping("/{id}")
    public ProductImageEntity updateImage(@PathVariable Long id, @RequestBody ProductImageEntity image) {
        // TODO: implement
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        // TODO: implement
    }


    @PostMapping("/{id}/set-primary")
    public void setPrimaryImage(@PathVariable Long id) {
        // TODO: implement
    }
//    getImagesByProductId
//
//getImageById
//
//addImageToProduct
//
//updateImage
//
//deleteImage
}
