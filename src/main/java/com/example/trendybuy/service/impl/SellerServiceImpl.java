package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.SellerProfileEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.SellerProfileRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.request.SellerRegisterRequest;
import com.example.trendybuy.dto.response.SellerProfileResponse;
import com.example.trendybuy.enums.SellerStatus;
import com.example.trendybuy.enums.UserRole;
import com.example.trendybuy.exception.AlreadyExistsException;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.service.SellerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService {

    private final UserRepository userRepository;
    private final SellerProfileRepository sellerProfileRepository;
    private final PasswordEncoder passwordEncoder;


//    ,seller ucun qeydiyyatdan kecmek daha cetindir cox sey teleb olur Əgər sən real ödəniş gateway (PayPal/Stripe/Payme/WayForPay) daxil edəcəksən, PaymentServiceImpl-i gateway ilə dəyiş və webhook endpoint əlavə et. Webhook gəldikdə SellerProfile-i aktivləşdir. Seller sənədləri üçün fayl server (S3/MinIO) istifadə et.
    @Override
    public SellerProfileResponse registerSeller(SellerRegisterRequest request) {

        // Email artıq var idisə, error
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(ExceptionCode.USER_ALREADY_EXISTS);
        }

        // 1) UserEntity yarat – rolu SELLER
        UserEntity user = new UserEntity();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setUserName(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.SELLER);
        user.setActive(true); // istəsən burada false saxlayıb ayrıca təsdiq də tələb edə bilərsən
        user.setRegistrationDate(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        // 2) SellerProfileEntity yarat
        SellerProfileEntity seller = new SellerProfileEntity();
        seller.setUser(user);
        seller.setShopName(request.getShopName());
        seller.setCompanyName(request.getCompanyName());
        seller.setTaxNumber(request.getTaxNumber());
        seller.setIban(request.getIban());
        seller.setStatus(SellerStatus.PENDING); // default olaraq PENDING

        sellerProfileRepository.save(seller);

        // 3) DTO-ya map et və qaytar
        return toResponse(seller);
    }

    @Override
    public SellerProfileResponse getMyProfile() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));

        SellerProfileEntity seller = sellerProfileRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.SELLER_NOT_FOUND));

        return toResponse(seller);
    }

    private SellerProfileResponse toResponse(SellerProfileEntity seller) {
        SellerProfileResponse dto = new SellerProfileResponse();
        dto.setSellerId(seller.getSellerId());
        dto.setShopName(seller.getShopName());
        dto.setCompanyName(seller.getCompanyName());
        dto.setTaxNumber(seller.getTaxNumber());
        dto.setIban(seller.getIban());
        dto.setStatus(seller.getStatus());

        UserEntity user = seller.getUser();
        if (user != null) {
            dto.setEmail(user.getEmail());
            dto.setFullName(user.getFullName());
        }

        return dto;
    }
}
