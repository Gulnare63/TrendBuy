package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.WishlistEntity;
import com.example.trendybuy.dto.response.WishlistResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    @Mapping(target = "wishlistId", source = "id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productPrice", source = "product.price")
    @Mapping(target = "productActive", source = "product.active")
    WishlistResponse toResponse(WishlistEntity entity);

    List<WishlistResponse> toResponseList(List<WishlistEntity> entities);
}
