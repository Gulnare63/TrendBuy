package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.ShoppingAddresEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.ShoppingAddressRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.request.ShoppingAddressRequest;
import com.example.trendybuy.dto.response.ShoppingAddressResponse;
import com.example.trendybuy.exception.ExceptionCode;
import com.example.trendybuy.exception.NotFoundException;
import com.example.trendybuy.mapper.AddressMapper;
import com.example.trendybuy.service.ShoppingAdressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingAdressServiceImpl implements ShoppingAdressService {

    private final ShoppingAddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    @Override
    public ShoppingAddressResponse addAddress(ShoppingAddressRequest request) {
        UserEntity currentUser = getCurrentUser();

        // Əgər ilk ünvandırsa və ya request-də default seçilibsə
        List<ShoppingAddresEntity> myAddresses = addressRepository.findByUser_UserId(currentUser.getUserId());
        boolean isDefault = request.isDefaultAddress();
        if (myAddresses.isEmpty()) {
            isDefault = true;
        }

        if (isDefault) {
            clearOtherDefaults(currentUser.getUserId());
        }

        ShoppingAddresEntity entity = new ShoppingAddresEntity();
        entity.setUser(currentUser);
        entity.setAddress(request.getAddress());
        entity.setCity(request.getCity());
        entity.setPostalCode(request.getPostalCode());
        entity.setDefaultAddress(isDefault);
        entity.setCreatedAt(LocalDateTime.now());

        return addressMapper.toResponse(addressRepository.save(entity));
    }

    @Override
    public ShoppingAddressResponse updateAddress(Long id, ShoppingAddressRequest request) {
        UserEntity currentUser = getCurrentUser();
        ShoppingAddresEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR));
                
        if (!entity.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new IllegalArgumentException("Action not allowed");
        }

        entity.setAddress(request.getAddress());
        entity.setCity(request.getCity());
        entity.setPostalCode(request.getPostalCode());

        if (request.isDefaultAddress() && !entity.isDefaultAddress()) {
            clearOtherDefaults(currentUser.getUserId());
            entity.setDefaultAddress(true);
        }

        return addressMapper.toResponse(addressRepository.save(entity));
    }

    @Override
    public void deleteAddress(Long id) {
        UserEntity currentUser = getCurrentUser();
        ShoppingAddresEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR));
                
        if (!entity.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new IllegalArgumentException("Action not allowed");
        }
        
        addressRepository.delete(entity);
    }

    @Override
    public void setDefaultAddress(Long id) {
        UserEntity currentUser = getCurrentUser();
        ShoppingAddresEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.VALIDATION_ERROR));
                
        if (!entity.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new IllegalArgumentException("Action not allowed");
        }

        clearOtherDefaults(currentUser.getUserId());
        entity.setDefaultAddress(true);
        addressRepository.save(entity);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ShoppingAddressResponse> getMyAddresses() {
        return addressMapper.toResponseList(addressRepository.findByUser_UserId(getCurrentUser().getUserId()));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public ShoppingAddressResponse getMyDefaultAddress() {
        return addressRepository.findByUser_UserIdAndDefaultAddressTrue(getCurrentUser().getUserId())
                .map(addressMapper::toResponse)
                .orElse(null);
    }

    // -- Helper
    private void clearOtherDefaults(Long userId) {
        List<ShoppingAddresEntity> myAddresses = addressRepository.findByUser_UserId(userId);
        for (ShoppingAddresEntity addr : myAddresses) {
            if (addr.isDefaultAddress()) {
                addr.setDefaultAddress(false);
                addressRepository.save(addr);
            }
        }
    }

    private UserEntity getCurrentUser() {
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.valueOf(userIdStr);
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.USER_NOT_FOUND));
    }
}
