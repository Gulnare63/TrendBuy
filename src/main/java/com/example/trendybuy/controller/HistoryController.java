package com.example.trendybuy.controller;

import com.example.trendybuy.dto.response.HistoryResponse;
import com.example.trendybuy.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public List<HistoryResponse> getMyHistory() {
        return historyService.getMyHistory();
    }

    @GetMapping("/order/{orderId}")
    public List<HistoryResponse> getOrderHistory(@PathVariable Long orderId) {
        return historyService.getOrderHistory(orderId);
    }
}
