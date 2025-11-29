package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.ProductReviewEntity;
import com.example.trendybuy.dto.response.UserReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


//    @Mapping(target = "productId", source = "product.id")
//    @Mapping(target = "productName", source = "product.name")
    UserReviewResponse toResponse(ProductReviewEntity entity);

    List<UserReviewResponse> toResponseList(List<ProductReviewEntity> entities);
}
