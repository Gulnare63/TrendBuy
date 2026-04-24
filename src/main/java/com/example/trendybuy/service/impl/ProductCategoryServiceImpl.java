package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.ProductCategoryEntity;
import com.example.trendybuy.dao.repository.ProductCategoryRepository;
import com.example.trendybuy.dto.request.CategoryCreateRequest;
import com.example.trendybuy.dto.request.CategoryUpdateRequest;
import com.example.trendybuy.dto.response.CategoryResponse;
import com.example.trendybuy.exception.AlreadyExistsException;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.CategoryMapper;
import com.example.trendybuy.service.ProductCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "categories", allEntries = true)
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        // Eyni adlı kateqoriya varmı?
        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new AlreadyExistsException(ExceptionCode.CATEGORY_ALREADY_EXISTS);
        }

        ProductCategoryEntity entity = categoryMapper.toEntity(request);

        // Parent kateqoriya varsa təyin et
        if (request.getParentId() != null) {
            ProductCategoryEntity parent = findCategory(request.getParentId());
            entity.setParent(parent);
        }

        ProductCategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.toResponse(saved);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toResponse(findCategory(id));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    @org.springframework.cache.annotation.Cacheable(value = "categories", key = "'allRoot'")
    public List<CategoryResponse> getAllRootCategories() {
        return categoryMapper.toResponseList(
                categoryRepository.findAllByParentIsNull()
        );
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CategoryResponse> getChildrenByParentId(Long parentId) {
        // Parent mövcuddur mu kontrol et
        findCategory(parentId);
        return categoryMapper.toResponseList(
                categoryRepository.findAllByParentId(parentId)
        );
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    @org.springframework.cache.annotation.Cacheable(value = "categories", key = "'activeRoot'")
    public List<CategoryResponse> getActiveRootCategories() {
        return categoryMapper.toResponseList(
                categoryRepository.findAllByActiveTrueAndParentIsNull()
        );
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "categories", allEntries = true)
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        ProductCategoryEntity entity = findCategory(id);

        // Ad dəyişdiriləcəksə, başqasında mövcud olmamalıdır
        if (request.getCategoryName() != null
                && !request.getCategoryName().equals(entity.getCategoryName())
                && categoryRepository.existsByCategoryName(request.getCategoryName())) {
            throw new AlreadyExistsException(ExceptionCode.CATEGORY_ALREADY_EXISTS);
        }

        if (request.getCategoryName() != null) {
            entity.setCategoryName(request.getCategoryName());
        }

        // Parent dəyişdiriləcəksə set et
        if (request.getParentId() != null) {
            ProductCategoryEntity parent = findCategory(request.getParentId());
            entity.setParent(parent);
        }

        ProductCategoryEntity updated = categoryRepository.save(entity);
        return categoryMapper.toResponse(updated);
    }

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "categories", allEntries = true)
    public void deactivateCategory(Long id) {
        ProductCategoryEntity entity = findCategory(id);
        entity.setActive(false);
        categoryRepository.save(entity);
    }

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "categories", allEntries = true)
    public void activateCategory(Long id) {
        ProductCategoryEntity entity = findCategory(id);
        entity.setActive(true);
        categoryRepository.save(entity);
    }

    @Override
    @org.springframework.cache.annotation.CacheEvict(value = "categories", allEntries = true)
    public void deleteCategory(Long id) {
        ProductCategoryEntity entity = findCategory(id);

        // Alt-kateqoriyası varsa silmə
        if (categoryRepository.existsByParentId(id)) {
            throw new IllegalStateException("Bu kateqoriyanın alt-kateqoriyaları var. Əvvəlcə onları silin.");
        }

        // Məhsulu varsa silmə
        if (categoryRepository.hasProducts(id)) {
            throw new IllegalStateException("Bu kateqoriyaya aid məhsullar var. Əvvəlcə onları silin.");
        }

        categoryRepository.delete(entity);
    }

    // ============ Private Helper ============
    private ProductCategoryEntity findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.CATEGORY_NOT_FOUND));
    }
}
