package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.ProductImageRequest;
import com.example.trendybuy.dto.response.ProductImageResponse;
import com.example.trendybuy.service.ProductImagesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImagesService imagesService;

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ProductImageResponse addImage(@Valid @RequestBody ProductImageRequest request) {
        return imagesService.addImage(request);
    }

    @GetMapping("/product/{productId}")
    public List<ProductImageResponse> getProductImages(@PathVariable Long productId) {
        return imagesService.getProductImages(productId);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        imagesService.deleteImage(id);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/{id}/primary")
    public void setPrimaryImage(@PathVariable Long id) {
        imagesService.setPrimaryImage(id);
    }
}
