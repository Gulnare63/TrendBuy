package com.example.trendybuy.controller;

import com.example.trendybuy.dto.response.OrderItemResponse;
import com.example.trendybuy.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/order/{orderId}")
    public List<OrderItemResponse> getItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.getItemsByOrderId(orderId);
    }
}
