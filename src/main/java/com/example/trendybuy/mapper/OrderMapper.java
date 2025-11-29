package com.example.trendybuy.mapper;


import com.example.trendybuy.dao.entity.OrderEntity;
import com.example.trendybuy.dto.request.OrderCreateRequest;
import com.example.trendybuy.dto.request.OrderUpdateRequest;
import com.example.trendybuy.dto.response.OrderResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                OrderItemMapper.class,
                PaymentMapper.class
        }
)
public interface OrderMapper {

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "items", source = "orderitems")
    @Mapping(target = "payments", source = "payments")
    OrderResponse toResponse(OrderEntity entity);

    List<OrderResponse> toResponseList(List<OrderEntity> entities);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "orderitems", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "history", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderEntity toEntity(OrderCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromRequest(OrderUpdateRequest request,
                                @MappingTarget OrderEntity entity);
}

