package com.example.trendybuy.mapper;


import com.example.trendybuy.dao.entity.PaymentEntity;
import com.example.trendybuy.dto.response.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "Id", source = "paymentId")
    PaymentResponse toResponse(PaymentEntity entity);
    List<PaymentResponse> toResponseList(List<PaymentEntity> entities);
}

