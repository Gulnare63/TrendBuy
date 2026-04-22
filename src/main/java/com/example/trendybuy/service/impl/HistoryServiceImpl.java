package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.HistoryEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.HistoryRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.response.HistoryResponse;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.HistoryMapper;
import com.example.trendybuy.service.HistoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final HistoryMapper historyMapper;

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<HistoryResponse> getMyHistory() {
        UserEntity currentUser = getCurrentUser();
        List<HistoryEntity> history = historyRepository.findByUser_UserIdOrderByChangeTimestampDesc(currentUser.getUserId());
        return historyMapper.toResponseList(history);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<HistoryResponse> getOrderHistory(Long orderId) {
        // Todo: Istifadəçinin bu sifarişə baxmağa icazəsi varmı yoxlanmalıdır (OrderRepository vasitəsi ilə)
        List<HistoryEntity> history = historyRepository.findByOrder_IdOrderByChangeTimestampDesc(orderId);
        return historyMapper.toResponseList(history);
    }

    private UserEntity getCurrentUser() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));
    }
}
