package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.request.UserCreateRequest;
import com.example.trendybuy.dto.request.UserUpdateRequest;
import com.example.trendybuy.dto.response.*;
import com.example.trendybuy.enums.UserRole;
import com.example.trendybuy.exception.AlreadyExistsException;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.exception.PasswordCannotMatchException;
import com.example.trendybuy.mapper.*;
import com.example.trendybuy.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final OrderSummaryMapper orderMapper;
    private final NotificationMapper notificationMapper;
    private final AddressMapper addressMapper;
    private final CartMapper cartMapper;
    private final ReviewMapper reviewMapper;
    private final HistoryMapper historyMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           OrderSummaryMapper orderMapper,
                           NotificationMapper notificationMapper,
                           AddressMapper addressMapper,
                           CartMapper cartMapper,
                           ReviewMapper reviewMapper,
                           HistoryMapper historyMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.orderMapper = orderMapper;
        this.notificationMapper = notificationMapper;
        this.addressMapper = addressMapper;
        this.cartMapper = cartMapper;
        this.reviewMapper = reviewMapper;
        this.historyMapper = historyMapper;
    }



    @Override
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AlreadyExistsException(ExceptionCode.USER_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(ExceptionCode.USER_ALREADY_EXISTS);
        }

        var entity = userMapper.toEntity(request);

        if (entity.getRole() == null) {
            entity.setRole(UserRole.CUSTOMER);
        }
        entity.setActive(true);

        var saved = userRepository.save(entity);
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public UserResponse getUserById(Long id) {
        var entity = findUser(id);
        return userMapper.toResponse(entity);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<UserResponse> getAllUsers() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        var entity = findUser(id);

        userMapper.updateEntityFromDto(request, entity);

        var updated = userRepository.save(entity);
        return userMapper.toResponse(updated);
    }

    @Override
    public void deleteUser(Long id) {
        var entity = findUser(id);
        //  soft delete etmeyi sorus mellimden
        userRepository.delete(entity);
    }



    // login() metodu UserService-dən silindi.
    // Düzgün login axını AuthService.login() → AuthService.verifyLoginOtp() vasitəsilə gedir.


    @Override
    public void activateUser(Long id) {
        var entity = findUser(id);
        entity.setActive(true);
    }

    @Override
    public void deactivateUser(Long id) {
        var entity = findUser(id);
        entity.setActive(false);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<UserResponse> getUsersByRole(String role) {
        var r = UserRole.valueOf(role.toUpperCase());
        return userMapper.toResponseList(userRepository.findAllByRole(r));
    }



    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<OrderSummaryResponse> getUserOrders(Long userId) {
        var user = findUser(userId);
        // user.getOrders() -> List<OrderEntity>
        return orderMapper.toResponseList(user.getOrders());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<NotificationResponse> getUserNotifications(Long userId) {
        var user = findUser(userId);
        return notificationMapper.toResponseList(user.getNotifications());
    }

    @Override
    public void markAllNotificationsRead(Long userId) {
        var user = findUser(userId);
        if (user.getNotifications() != null) {
            user.getNotifications().forEach(n -> n.setRead(true)); // entity-də isRead-dirsə, orada setIsRead(true) olacaq
        }
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<AddressResponse> getUserAddresses(Long userId) {
        var user = findUser(userId);
        return addressMapper.toResponseList(user.getShoppingAddresses());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CartItemResponse> getUserCart(Long userId) {
        var user = findUser(userId);
        return cartMapper.toResponseList(user.getShoppingCarts());
    }

    @Override
    public void clearUserCart(Long userId) {
        var user = findUser(userId);

        // bu variant yalnız o halda DB-də siləcək ki,
        // UserEntity-də @OneToMany(..., cascade = ALL, orphanRemoval = true) olsun
        if (user.getShoppingCarts() != null) {
            user.getShoppingCarts().clear();
        }


    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<UserReviewResponse> getUserReviews(Long userId) {
        var user = findUser(userId);
        return reviewMapper.toResponseList(user.getProductReviews());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<HistoryResponse> getUserHistory(Long userId) {
        var user = findUser(userId);
        return historyMapper.toResponseList(user.getHistory());
    }

    private UserEntity findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));
    }
}
