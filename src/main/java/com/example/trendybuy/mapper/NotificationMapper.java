package com.example.trendybuy.mapper;

import com.example.trendybuy.dao.entity.NotificationEntity;
import com.example.trendybuy.dto.response.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

//    @Mapping(target = "id", source = "notificationId")
//    @Mapping(target = "read", source = "isRead")
    NotificationResponse toResponse(NotificationEntity entity);

    List<NotificationResponse> toResponseList(List<NotificationEntity> entities);
}
