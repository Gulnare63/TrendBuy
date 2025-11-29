package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.OrderEntity;
import com.example.trendybuy.dto.response.OrderSummaryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderSummaryMapper {

    OrderSummaryResponse toResponse(OrderEntity entity);

    List<OrderSummaryResponse> toResponseList(List<OrderEntity> entities);
}
