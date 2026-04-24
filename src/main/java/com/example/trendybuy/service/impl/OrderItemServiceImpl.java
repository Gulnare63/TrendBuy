package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.repository.OrderItemRepository;
import com.example.trendybuy.dto.response.OrderItemResponse;
import com.example.trendybuy.mapper.OrderItemMapper;
import com.example.trendybuy.service.OrderItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.SUPPORTS)
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponse> getItemsByOrderId(Long orderId) {
        return orderItemMapper.toResponseList(
                orderItemRepository.findByOrder_Id(orderId)
        );
    }
}
