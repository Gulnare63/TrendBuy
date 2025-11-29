package com.example.trendybuy.mapper;


import com.example.trendybuy.dao.entity.OrderItemEntity;
import com.example.trendybuy.dto.response.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

//    @Mapping(target = "orderItemId", source = "id")
//    @Mapping(target = "productId", source = "product.id")
//    @Mapping(target = "productName", source = "product.name")
//    @Mapping(target = "lineTotal", expression = "java( calcLineTotal(entity) )")
    OrderItemResponse toResponse(OrderItemEntity entity);

    List<OrderItemResponse> toResponseList(List<OrderItemEntity> entities);

    default BigDecimal calcLineTotal(OrderItemEntity entity) {
        if (entity == null || entity.getPrice() == null || entity.getQuantity() == null) {
            return BigDecimal.ZERO;
        }
        return entity.getPrice().multiply(BigDecimal.valueOf(entity.getQuantity()));
    }
}
