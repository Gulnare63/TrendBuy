package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.ProductCreateRequest;
import com.example.trendybuy.dto.request.ProductUpdateRequest;
import com.example.trendybuy.dto.response.ProductResponse;
import com.example.trendybuy.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return productService.searchProducts(name, categoryId, minPrice, maxPrice);
    }

    @GetMapping("/top-rated")
    public List<ProductResponse> getTopRatedProducts() {
        return productService.getTopRatedProducts();
    }

    @GetMapping("/on-discount")
    public List<ProductResponse> getProductsOnDiscount() {
        return productService.getProductsOnDiscount();
    }

    @GetMapping("/by-category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }


    // Seller-only metodlar (yalnız ROLES_SELLER olanlar istifadə edə bilər)

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return productService.createProduct(request);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(id, request);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    
    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/my-products")
    public List<ProductResponse> getMyProducts() {
        return productService.getMyProducts();
    }
    
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/{id}/activate")
    public void activateProduct(@PathVariable Long id) {
        productService.activateProduct(id);
    }
    
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/{id}/deactivate")
    public void deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
    }
}
