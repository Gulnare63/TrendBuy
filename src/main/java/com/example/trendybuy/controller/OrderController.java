package com.example.trendybuy.controller;

import com.example.trendybuy.dto.request.OrderCreateRequest;
import com.example.trendybuy.dto.request.OrderStatusUpdateRequest;
import com.example.trendybuy.dto.response.OrderResponse;
import com.example.trendybuy.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/from-cart")
    public OrderResponse createOrderFromCart() {
        return orderService.createOrderFromCart();
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/my-orders")
    public List<OrderResponse> getMyOrders() {
        String userIdStr = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.getOrdersByUser(Long.valueOf(userIdStr));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/status")
    public void updateOrderStatus(@PathVariable Long orderId, @Valid @RequestBody OrderStatusUpdateRequest request) {
        orderService.updateOrderStatus(orderId, request);
    }
}
