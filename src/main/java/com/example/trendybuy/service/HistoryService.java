package com.example.trendybuy.service;

import com.example.trendybuy.dto.response.HistoryResponse;

import java.util.List;

public interface HistoryService {

    List<HistoryResponse> getMyHistory();

    List<HistoryResponse> getOrderHistory(Long orderId);
}
