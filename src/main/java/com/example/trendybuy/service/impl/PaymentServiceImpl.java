package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.OrderEntity;
import com.example.trendybuy.dao.entity.OrderItemEntity;
import com.example.trendybuy.dao.entity.PaymentEntity;
import com.example.trendybuy.dao.repository.OrderRepository;
import com.example.trendybuy.dao.repository.PaymentRepository;
import com.example.trendybuy.dao.repository.ProductRepository;
import com.example.trendybuy.dto.request.PaymentRequest;
import com.example.trendybuy.dto.response.PaymentResponse;
import com.example.trendybuy.enums.OrderStatus;
import com.example.trendybuy.enums.PaymentStatus;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.PaymentMapper;
import com.example.trendybuy.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;
    private final ProductRepository productRepository; // Stok yoxlanışı üçün

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        OrderEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.ORDER_NOT_FOUND));

        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("Cannot pay for an order with status: " + order.getStatus());
        }

        // Simulyasiya: Dərhal SUCCESS veririk. (Real layihədə burada Stripe/PayPal api çağırılacaq)
        PaymentEntity payment = new PaymentEntity();
        payment.setOrder(order);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setProviderRef(request.getProviderRef() != null ? request.getProviderRef() : "SIM_" + System.currentTimeMillis());
        payment.setPaymentDate(LocalDateTime.now());

        PaymentEntity savedPayment = paymentRepository.save(payment);

        // Ödəniş SUCCESS olduqdan sonra həm Sifarişin statusunu dəyişirik, həm də MƏHSUL STOKUNU AZALDIRIQ
        order.setStatus(OrderStatus.PROCESSING); // Məsələn Processing, çünki artıq ödənilib
        
        for (OrderItemEntity item : order.getOrderitems()) {
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock remaining for product: " + item.getProduct().getName());
            }
            item.getProduct().setStockQuantity(item.getProduct().getStockQuantity() - item.getQuantity());
            productRepository.save(item.getProduct());
        }
        
        orderRepository.save(order);

        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public PaymentResponse getPaymentById(Long id) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR)); 
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PaymentResponse> getPaymentsByOrderId(Long orderId) {
        return paymentMapper.toResponseList(paymentRepository.findByOrder_Id(orderId));
    }
}
