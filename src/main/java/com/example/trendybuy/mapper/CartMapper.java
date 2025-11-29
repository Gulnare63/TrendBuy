package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.ShoppingCartEntity;
import com.example.trendybuy.dto.response.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

//    @Mapping(target = "cartItemId", source = "cartId")
//    @Mapping(target = "productId", source = "product.id")
//    @Mapping(target = "productName", source = "product.name")
//    @Mapping(target = "unitPrice", source = "product.price")
//    @Mapping(target = "lineTotal", expression = "java( entity.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(entity.getQuantity())) )")
    CartItemResponse toResponse(ShoppingCartEntity entity);

    List<CartItemResponse> toResponseList(List<ShoppingCartEntity> entities);
}
