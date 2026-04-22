package com.example.trendybuy.service;

import com.example.trendybuy.dto.request.UserCreateRequest;
import com.example.trendybuy.dto.request.UserUpdateRequest;
import com.example.trendybuy.dto.response.*;


import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);


    void activateUser(Long id);

    void deactivateUser(Long id);

    List<UserResponse> getUsersByRole(String role);





    List<OrderSummaryResponse> getUserOrders(Long userId);


    List<NotificationResponse> getUserNotifications(Long userId);
    void markAllNotificationsRead(Long userId);


    List<AddressResponse> getUserAddresses(Long userId);


    List<CartItemResponse> getUserCart(Long userId);
    void clearUserCart(Long userId);


    List<UserReviewResponse> getUserReviews(Long userId);


    List<HistoryResponse> getUserHistory(Long userId);
}
