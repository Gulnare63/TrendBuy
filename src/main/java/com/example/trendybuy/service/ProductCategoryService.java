package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.CategoryCreateRequest;
import com.example.trendybuy.dto.request.CategoryUpdateRequest;
import com.example.trendybuy.dto.response.CategoryResponse;

import java.util.List;

public interface ProductCategoryService {

    // Yeni kateqoriya yarat (ana və ya alt)
    CategoryResponse createCategory(CategoryCreateRequest request);

    // ID-yə görə kateqoriya gətir
    CategoryResponse getCategoryById(Long id);

    // Bütün ana kateqoriyaları gətir (alt-kateqoriyaları ilə birlikdə)
    List<CategoryResponse> getAllRootCategories();

    // Müəyyən parent-ə aid alt-kateqoriyaları gətir
    List<CategoryResponse> getChildrenByParentId(Long parentId);

    // Yalnız aktiv ana kateqoriyaları gətir (front-end üçün)
    List<CategoryResponse> getActiveRootCategories();

    // Bütün kateqoriyaları gətir (Admin panel üçün)
    List<CategoryResponse> getAllCategories();

    // Kateqoriyanı yenilə
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);

    // Kateqoriyanı deaktiv et (Soft Delete)
    void deactivateCategory(Long id);

    // Kateqoriyanı aktiv et
    void activateCategory(Long id);

    // Kateqoriyanı tam sil (Yalnız alt-kateqoriyası və məhsulu yoxdursa)
    void deleteCategory(Long id);
}
