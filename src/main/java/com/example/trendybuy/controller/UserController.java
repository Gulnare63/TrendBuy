package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.UserCreateRequest;
import com.example.trendybuy.dto.request.UserUpdateRequest;
import com.example.trendybuy.dto.response.*;
import com.example.trendybuy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id,
                                   @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }




    @PostMapping("/{id}/activate")
    public void activateUser(@PathVariable Long id) {
        userService.activateUser(id);
    }

    @PostMapping("/{id}/deactivate")
    public void deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
    }



    @GetMapping("/role/{role}")
    public List<UserResponse> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }



    @GetMapping("/{id}/orders")
    public List<OrderSummaryResponse> getUserOrders(@PathVariable Long id) {
        return userService.getUserOrders(id);
    }

    @GetMapping("/{id}/notifications")
    public List<NotificationResponse> getUserNotifications(@PathVariable Long id) {
        return userService.getUserNotifications(id);
    }

    @PostMapping("/{id}/notifications/read-all")
    public void markUserNotificationsAsRead(@PathVariable Long id) {
        userService.markAllNotificationsRead(id);
    }

    @GetMapping("/{id}/addresses")
    public List<ShoppingAddressResponse> getUserAddresses(@PathVariable Long id) {
        return userService.getUserAddresses(id);
    }

    @GetMapping("/{id}/cart")
    public List<CartItemResponse> getUserCart(@PathVariable Long id) {
        return userService.getUserCart(id);
    }

    @DeleteMapping("/{id}/cart")
    public void clearUserCart(@PathVariable Long id) {
        userService.clearUserCart(id);
    }

    @GetMapping("/{id}/reviews")
    public List<UserReviewResponse> getUserReviews(@PathVariable Long id) {
        return userService.getUserReviews(id);
    }

    @GetMapping("/{id}/history")
    public List<HistoryResponse> getUserHistory(@PathVariable Long id) {
        return userService.getUserHistory(id);
    }
}
