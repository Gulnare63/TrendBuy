package com.example.trendybuy.service.impl;


import com.example.trendybuy.dao.entity.*;
import com.example.trendybuy.dao.repository.*;

import com.example.trendybuy.dto.request.OrderCreateRequest;
import com.example.trendybuy.dto.request.OrderItemCreateRequest;
import com.example.trendybuy.dto.request.OrderStatusUpdateRequest;
import com.example.trendybuy.dto.request.OrderUpdateRequest;
import com.example.trendybuy.dto.response.*;
import com.example.trendybuy.enums.OrderStatus;
import com.example.trendybuy.enums.PaymentStatus;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.*;
import com.example.trendybuy.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;

    private final OrderMapper orderMapper;
    private final OrderSummaryMapper orderSummaryMapper;
    private final OrderItemMapper orderItemMapper;
    private final PaymentMapper paymentMapper;
    private final HistoryMapper historyMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository,
                            OrderItemRepository orderItemRepository,
                            PaymentRepository paymentRepository,
                            OrderMapper orderMapper,
                            OrderSummaryMapper orderSummaryMapper,
                            OrderItemMapper orderItemMapper,
                            PaymentMapper paymentMapper,
                            HistoryMapper historyMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.orderMapper = orderMapper;
        this.orderSummaryMapper = orderSummaryMapper;
        this.orderItemMapper = orderItemMapper;
        this.paymentMapper = paymentMapper;
        this.historyMapper = historyMapper;
    }



    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));


        OrderEntity order = orderMapper.toEntity(request);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setUpdatedAt(LocalDateTime.now());

        if (order.getTotalAmount() == null) {
            order.setTotalAmount(BigDecimal.ZERO);
        }
        if (order.getDiscountAmount() == null) {
            order.setDiscountAmount(BigDecimal.ZERO);
        }

        OrderEntity savedOrder = orderRepository.save(order);


        if (request.getItems() != null && !request.getItems().isEmpty()) {
            List<OrderItemEntity> itemEntities = new ArrayList<>();
            for (OrderItemCreateRequest itemReq : request.getItems()) {

                ProductEntity product = productRepository.findById(itemReq.getProductId())
                        .orElseThrow(() -> new NotFoundException(ExceptionCode.PRODUCT_NOT_FOUND));

                OrderItemEntity item = new OrderItemEntity();
                item.setOrder(savedOrder);
                item.setProduct(product);
                item.setQuantity(itemReq.getQuantity());
                item.setPrice(itemReq.getPrice());

                itemEntities.add(item);
            }
            orderItemRepository.saveAll(itemEntities);
            savedOrder.setOrderitems(itemEntities);


            if (request.getTotalAmount() == null) {
                BigDecimal total = itemEntities.stream()
                        .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                savedOrder.setTotalAmount(total);
            }
        }


        return orderMapper.toResponse(savedOrder);
    }

    /* =============== READ =============== */

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public OrderResponse getOrderById(Long orderId) {
        OrderEntity order = findOrder(orderId);
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<OrderResponse> getAllOrders() {
        return orderMapper.toResponseList(orderRepository.findAll());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<OrderSummaryResponse> getAllOrdersSummary() {
        return orderSummaryMapper.toResponseList(orderRepository.findAll());
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<OrderResponse> getOrdersByUser(Long userId) {
        List<OrderEntity> orders = orderRepository.findByUser_UserId(userId);
        return orderMapper.toResponseList(orders);
    }



    @Override
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        OrderEntity order = findOrder(orderId);
        orderMapper.updateOrderFromRequest(request, order);
        order.setUpdatedAt(LocalDateTime.now());
        return orderMapper.toResponse(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
        OrderEntity order = findOrder(orderId);
        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }
        if (request.getPaymentStatus() != null) {
            order.setPaymentStatus(request.getPaymentStatus());
        }
        order.setUpdatedAt(LocalDateTime.now());
    }



    @Override
    public void deleteOrder(Long orderId) {
        OrderEntity order = findOrder(orderId);
        orderRepository.delete(order);
    }



    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<OrderItemResponse> getOrderItems(Long orderId) {
        // burda order.getOrderItems() da etsek nece olar ?
        List<OrderItemEntity> items = orderItemRepository.findByOrder_Id(orderId);
        return orderItemMapper.toResponseList(items);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PaymentResponse> getOrderPayments(Long orderId) {
        List<PaymentEntity> payments = paymentRepository.findByOrder_id(orderId);
        return paymentMapper.toResponseList(payments);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<HistoryResponse> getOrderHistory(Long orderId) {
        OrderEntity order = findOrder(orderId);
        return historyMapper.toResponseList(order.getHistory());
    }



    private OrderEntity findOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.ORDER_NOT_FOUND));
    }
}

