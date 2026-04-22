package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.CategoryCreateRequest;
import com.example.trendybuy.dto.request.CategoryUpdateRequest;
import com.example.trendybuy.dto.response.CategoryResponse;
import com.example.trendybuy.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    // ======================== PUBLIC APIs ========================

    /**
     * Aktiv ana kateqoriyaları gətirir (müştəri üçün - Navbar, Filter)
     * GET /api/categories/active
     */
    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponse>> getActiveCategories() {
        return ResponseEntity.ok(categoryService.getActiveRootCategories());
    }

    /**
     * Bir kateqoriyanın detallarını gətirir
     * GET /api/categories/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    /**
     * Bir parent kateqoriyanın alt-kateqoriyalarını gətirir
     * GET /api/categories/{parentId}/children
     */
    @GetMapping("/{parentId}/children")
    public ResponseEntity<List<CategoryResponse>> getChildren(@PathVariable Long parentId) {
        return ResponseEntity.ok(categoryService.getChildrenByParentId(parentId));
    }

    // ======================== ADMIN APIs ========================

    /**
     * Bütün kateqoriyaları gətirir (Admin panel)
     * GET /api/categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Bütün ana kateqoriyaları gətirir (aktiv + deaktiv)
     * GET /api/categories/roots
     */
    @GetMapping("/roots")
    public ResponseEntity<List<CategoryResponse>> getRootCategories() {
        return ResponseEntity.ok(categoryService.getAllRootCategories());
    }

    /**
     * Yeni kateqoriya yarat
     * POST /api/categories
     * Body: { "categoryName": "Elektronika", "parentId": null }
     */
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(request));
    }

    /**
     * Kateqoriyanı yenilə
     * PUT /api/categories/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    /**
     * Kateqoriyanı deaktiv et (Soft delete - məhsullar qalır)
     * PATCH /api/categories/{id}/deactivate
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCategory(@PathVariable Long id) {
        categoryService.deactivateCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deaktiv kateqoriyanı aktivləşdir
     * PATCH /api/categories/{id}/activate
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateCategory(@PathVariable Long id) {
        categoryService.activateCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Kateqoriyanı tam sil (yalnız alt-kateqoriyası və məhsulu yoxdursa)
     * DELETE /api/categories/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
