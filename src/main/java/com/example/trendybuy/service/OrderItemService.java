package com.example.trendybuy.service;

import com.example.trendybuy.dto.response.OrderItemResponse;
import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> getItemsByOrderId(Long orderId);
}
