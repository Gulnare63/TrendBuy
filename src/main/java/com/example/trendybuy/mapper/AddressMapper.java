package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.ShoppingAddresEntity;
import com.example.trendybuy.dto.response.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

//    @Mapping(target = "id", source = "addressId")
//    @Mapping(target = "defaultAddress", source = "isDefault")
    AddressResponse toResponse(ShoppingAddresEntity entity);

    List<AddressResponse> toResponseList(List<ShoppingAddresEntity> entities);
}

