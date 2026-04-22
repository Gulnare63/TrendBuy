package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.ProductImageEntity;
import com.example.trendybuy.dto.response.ProductImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(target = "productId", source = "product.id")
    ProductImageResponse toResponse(ProductImageEntity entity);

    List<ProductImageResponse> toResponseList(List<ProductImageEntity> entities);
}
