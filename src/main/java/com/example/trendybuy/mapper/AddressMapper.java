package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.ShoppingAddresEntity;
import com.example.trendybuy.dto.response.ShoppingAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    ShoppingAddressResponse toResponse(ShoppingAddresEntity entity);

    List<ShoppingAddressResponse> toResponseList(List<ShoppingAddresEntity> entities);
}

