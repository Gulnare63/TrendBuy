package com.example.trendybuy.controller;

import com.example.trendybuy.dao.entity.ProductCategoryEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {


    @GetMapping
    public List<ProductCategoryEntity> getAllCategories() {
        // TODO: implement
        return null;
    }

    @GetMapping("/{id}")
    public ProductCategoryEntity getCategoryById(@PathVariable Long id) {
        // TODO: implement
        return null;
    }


    @PostMapping
    public ProductCategoryEntity createCategory(@RequestBody ProductCategoryEntity category) {
        // TODO: implement
        return null;
    }

    @PutMapping("/{id}")
    public ProductCategoryEntity updateCategory(@PathVariable Long id, @RequestBody ProductCategoryEntity category) {
        // TODO: implement
        return null;
    }


    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        // TODO: implement
    }


    @GetMapping("/{id}/subcategories")
    public List<ProductCategoryEntity> getSubcategoriesByParentId(@PathVariable Long id) {
        // TODO: implement
        return null;
    }

    @GetMapping("/hierarchy")
    public List<ProductCategoryEntity> getCategoryHierarchy() {
        // TODO: implement
        return null;
    }
//    getAllCategories
//
//getCategoryById
//
//createCategory
//
//updateCategory
//
//deleteCategory
//
//getSubcategoriesByParentId

}
