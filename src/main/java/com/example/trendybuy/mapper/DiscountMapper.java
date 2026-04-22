package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.DiscountEntity;
import com.example.trendybuy.dto.request.DiscountRequest;
import com.example.trendybuy.dto.response.DiscountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "active", constant = "true")
    DiscountEntity toEntity(DiscountRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    DiscountResponse toResponse(DiscountEntity entity);

    List<DiscountResponse> toResponseList(List<DiscountEntity> entities);
}
