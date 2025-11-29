package com.example.trendybuy.mapper;


import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dto.request.UserCreateRequest;
import com.example.trendybuy.dto.request.UserDto;
import com.example.trendybuy.dto.request.UserUpdateRequest;
import com.example.trendybuy.dto.response.UserResponse;
import org.mapstruct.*;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "userId", ignore = true)
//    @Mapping(target = "isActive", expression = "java(true)")
//    @Mapping(target = "registrationDate", ignore = true)
//
//    @Mapping(target = "history", ignore = true)
//    @Mapping(target = "notifications", ignore = true)
//    @Mapping(target = "orders", ignore = true)
//    @Mapping(target = "productReviews", ignore = true)
//    @Mapping(target = "shoppingAddresses", ignore = true)
//    @Mapping(target = "shoppingCarts", ignore = true)
    UserEntity toEntity(UserCreateRequest dto);

//    @Mapping(target = "id", source = "userId")
//    @Mapping(target = "active", source = "isActive")
    UserResponse toResponse(UserEntity entity);

    List<UserResponse> toResponseList(List<UserEntity> entities);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateRequest dto, @MappingTarget UserEntity entity);


    @Mapping(target = "id", source = "userId")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "active", source = "active")
    UserDto toDto(UserEntity entity);
}
