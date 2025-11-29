package com.example.trendybuy.service;


import com.example.trendybuy.dto.request.OrderCreateRequest;
import com.example.trendybuy.dto.request.OrderStatusUpdateRequest;
import com.example.trendybuy.dto.request.OrderUpdateRequest;
import com.example.trendybuy.dto.response.*;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderCreateRequest request);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getAllOrders();

    List<OrderSummaryResponse> getAllOrdersSummary();

    List<OrderResponse> getOrdersByUser(Long userId);

    OrderResponse updateOrder(Long orderId, OrderUpdateRequest request);

    void updateOrderStatus(Long orderId, OrderStatusUpdateRequest request);

    void deleteOrder(Long orderId);

    List<OrderItemResponse> getOrderItems(Long orderId);

    List<PaymentResponse> getOrderPayments(Long orderId);

    List<HistoryResponse> getOrderHistory(Long orderId);
}

