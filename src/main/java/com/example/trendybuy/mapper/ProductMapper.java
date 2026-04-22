package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.ProductEntity;
import com.example.trendybuy.dto.request.ProductCreateRequest;
import com.example.trendybuy.dto.request.ProductUpdateRequest;
import com.example.trendybuy.dto.response.ProductResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "discount", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    ProductEntity toEntity(ProductCreateRequest request);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "seller.sellerId", target = "sellerId")
    @Mapping(source = "seller.shopName", target = "sellerShopName")
    ProductResponse toResponse(ProductEntity entity);

    List<ProductResponse> toResponseList(List<ProductEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "discount", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    void updateEntityFromRequest(ProductUpdateRequest request, @MappingTarget ProductEntity entity);
}
