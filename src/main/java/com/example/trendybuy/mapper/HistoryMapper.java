package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.HistoryEntity;
import com.example.trendybuy.dto.response.HistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

//    @Mapping(target = "id", source = "historyId")
//    @Mapping(target = "userId", source = "user.userId")
//    @Mapping(target = "orderId", source = "order.id")
//    @Mapping(target = "paymentId", source = "payment.Id")
//    @Mapping(target = "description", source = "changeDescription")
//    @Mapping(target = "type", source = "historyType")
//    @Mapping(target = "timestamp", source = "changeTimestamp")
    HistoryResponse toResponse(HistoryEntity entity);

    List<HistoryResponse> toResponseList(List<HistoryEntity> entities);
}
