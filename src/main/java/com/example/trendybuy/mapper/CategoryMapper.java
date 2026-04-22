package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.ProductCategoryEntity;
import com.example.trendybuy.dto.request.CategoryCreateRequest;
import com.example.trendybuy.dto.response.CategoryChildResponse;
import com.example.trendybuy.dto.response.CategoryResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // Create request -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)       // Service içində əl ilə set edirik
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "active", constant = "true")
    ProductCategoryEntity toEntity(CategoryCreateRequest request);

    // Entity -> Response (full)
    @Mapping(target = "parentId",   source = "parent.id")
    @Mapping(target = "parentName", source = "parent.categoryName")
    @Mapping(target = "productCount", expression = "java(entity.getProducts() == null ? 0 : entity.getProducts().size())")
    @Mapping(target = "children", expression = "java(toChildList(entity.getChildren()))")
    CategoryResponse toResponse(ProductCategoryEntity entity);

    // Entity -> Child Response (alt-kateqoriya üçün sadə versiya)
    @Mapping(target = "id",           source = "id")
    @Mapping(target = "categoryName", source = "categoryName")
    @Mapping(target = "active",       source = "active")
    CategoryChildResponse toChildResponse(ProductCategoryEntity entity);

    // List<Entity> -> List<Response>
    List<CategoryResponse> toResponseList(List<ProductCategoryEntity> entities);

    // List<Entity> -> List<ChildResponse>
    default List<CategoryChildResponse> toChildList(List<ProductCategoryEntity> children) {
        if (children == null) return List.of();
        return children.stream()
                .map(this::toChildResponse)
                .collect(Collectors.toList());
    }
}
