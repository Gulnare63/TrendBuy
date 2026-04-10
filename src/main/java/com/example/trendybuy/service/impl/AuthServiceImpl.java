package com.example.trendybuy.service.impl;

import com.example.trendybuy.dao.entity.SellerProfileEntity;
import com.example.trendybuy.dao.entity.UserEntity;
import com.example.trendybuy.dao.repository.SellerProfileRepository;
import com.example.trendybuy.dao.repository.UserRepository;
import com.example.trendybuy.dto.request.*;
import com.example.trendybuy.enums.OtpType;
import com.example.trendybuy.enums.SellerStatus;
import com.example.trendybuy.enums.UserRole;
import com.example.trendybuy.exception.*;
import com.example.trendybuy.mapper.UserMapper;
import com.example.trendybuy.service.AuthService;
import com.example.trendybuy.service.EmailService;
import com.example.trendybuy.service.JwtService;
import com.example.trendybuy.service.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.example.trendybuy.exception.ExceptionCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final SellerProfileRepository sellerProfileRepository;



    private static final long OTP_TTL = 300;

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    @Override
    public void registerWithEmail(EmailRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(ExceptionCode.USER_ALREADY_EXISTS);
        }

        UserEntity user = new UserEntity();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setUserName(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user.setActive(false);
        user.setRegistrationDate(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        String otp = generateOtp();
        otpService.saveOtp(OtpType.REGISTER_EMAIL, request.getEmail(), otp, OTP_TTL);
        log.info("REGISTER EMAIL OTP -> {}", otp);


        emailService.sendOtpEmail(request.getEmail(), otp);
    }


    @Override
    public AuthResponse verifyEmailOtp(VerifyEmailOtpRequest request) {

        String expectedOtp = otpService.getOtp(OtpType.REGISTER_EMAIL, request.getEmail());

        if (expectedOtp == null) {
            throw new OtpExpiredException(ExceptionCode.OTP_EXPIRED);
        }
        if (!expectedOtp.equals(request.getOtp())) {
            throw new InvalidOtpException(ExceptionCode.INVALID_OTP);
        }

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        user.setActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        otpService.deleteOtp(OtpType.REGISTER_EMAIL, request.getEmail());

        return AuthResponse.builder()
                .message("Account verified")
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .expiresIn(3600)
                .user(userMapper.toDto(user))
                .build();
    }


    @Override
    public void registerWithPhone(PhoneRegisterRequest request) {

        if (userRepository.existsByPhoneNumber(request.getPhone())) {
            throw new AlreadyExistsException(ExceptionCode.USER_ALREADY_EXISTS);
        }

        UserEntity user = new UserEntity();
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhone());
        user.setUserName(request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user.setActive(false);
        user.setRegistrationDate(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        String otp = generateOtp();
        otpService.saveOtp(OtpType.REGISTER_PHONE, request.getPhone(), otp, OTP_TTL);
        log.info("REGISTER PHONE OTP -> {}", otp);
    }


    @Override
    public AuthResponse verifyPhoneOtp(VerifyPhoneOtpRequest request) {

        String expectedOtp = otpService.getOtp(OtpType.REGISTER_PHONE, request.getPhone());

        if (expectedOtp == null) {
            throw new OtpExpiredException(ExceptionCode.OTP_EXPIRED);
        }
        if (!expectedOtp.equals(request.getOtp())) {
            throw new InvalidOtpException(ExceptionCode.INVALID_OTP);
        }

        UserEntity user = userRepository.findByPhoneNumber(request.getPhone())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        user.setActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        otpService.deleteOtp(OtpType.REGISTER_PHONE, request.getPhone());

        return AuthResponse.builder()
                .message("Account verified")
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .expiresIn(3600)
                .user(userMapper.toDto(user))
                .build();
    }

    @Override
    public void login(LoginRequest request) {

        UserEntity user;


        if (request.getIdentifier().contains("@")) {
            user = userRepository.findByEmail(request.getIdentifier())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        } else {
            user = userRepository.findByPhoneNumber(request.getIdentifier())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new PasswordCannotMatchException(ExceptionCode.PASSWORD_CANNOT_MATCH);
        }

        if (user.getRole() == UserRole.CUSTOMER && !user.isActive()) {
            throw new AccountNotActiveException(ExceptionCode.ACCOUNT_NOT_ACTIVE);
        }


        if (user.getRole() == UserRole.SELLER) {
            SellerProfileEntity seller = sellerProfileRepository.findByUser(user)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.SELLER_NOT_FOUND));

            if (seller.getStatus() != SellerStatus.ACTIVE) {
                throw new AccountNotActiveException(ExceptionCode.ACCOUNT_NOT_ACTIVE);
            }
        }


        String otp = generateOtp();
        otpService.saveOtp(OtpType.LOGIN, request.getIdentifier(), otp, OTP_TTL);
        log.info("LOGIN OTP -> {}", otp);

        if (request.getIdentifier().contains("@")) {
            emailService.sendOtpEmail(user.getEmail(), otp);
        }
    }

//    @Override
//    public void login(LoginRequest request) {
//
//        UserEntity user;
//
//        if (request.getIdentifier().contains("@")) {
//            user = userRepository.findByEmail(request.getIdentifier())
//                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
//        } else {
//            user = userRepository.findByPhoneNumber(request.getIdentifier())
//                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
//        }
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new PasswordCannotMatchException(ExceptionCode.PASSWORD_CANNOT_MATCH);
//        }
//
//        if (!user.isActive()) {
//            throw new AccountNotActiveException(ExceptionCode.ACCOUNT_NOT_ACTIVE);
//        }
//
//        String otp = generateOtp();
//        otpService.saveOtp(OtpType.LOGIN, request.getIdentifier(), otp, OTP_TTL);
//        log.info("LOGIN OTP -> {}", otp);
//
//
//        if (request.getIdentifier().contains("@")) {
//            emailService.sendOtpEmail(user.getEmail(), otp);
//        }
//    }


    @Override
    public AuthResponse verifyLoginOtp(VerifyLoginOtpRequest request) {

        String expectedOtp = otpService.getOtp(OtpType.LOGIN, request.getIdentifier());

        if (expectedOtp == null) {
            throw new OtpExpiredException(ExceptionCode.OTP_EXPIRED);
        }
        if (!expectedOtp.equals(request.getOtp())) {
            throw new InvalidOtpException(ExceptionCode.INVALID_OTP);
        }

        otpService.deleteOtp(OtpType.LOGIN, request.getIdentifier());

        UserEntity user;

        if (request.getIdentifier().contains("@")) {
            user = userRepository.findByEmail(request.getIdentifier())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        } else {
            user = userRepository.findByPhoneNumber(request.getIdentifier())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        }

        return AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .expiresIn(3600)
                .user(userMapper.toDto(user))
                .build();
    }


    @Override
    public void forgotPassword(ForgotPasswordRequest request) {

        Optional<UserEntity> userOpt;

        if (request.getIdentifier().contains("@")) {
            userOpt = userRepository.findByEmail(request.getIdentifier());
        } else {
            userOpt = userRepository.findByPhoneNumber(request.getIdentifier());
        }

        if (userOpt.isPresent()) {
            String otp = generateOtp();
            otpService.saveOtp(OtpType.PASSWORD_RESET, request.getIdentifier(), otp, OTP_TTL);
            log.info("PASSWORD RESET OTP -> {}", otp);


            if (request.getIdentifier().contains("@")) {
                emailService.sendOtpEmail(request.getIdentifier(), otp);
            }
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        String expectedOtp = otpService.getOtp(OtpType.PASSWORD_RESET, request.getIdentifier());

        if (expectedOtp == null) {
            throw new OtpExpiredException(ExceptionCode.OTP_EXPIRED);
        }
        if (!expectedOtp.equals(request.getOtp())) {
            throw new InvalidOtpException(ExceptionCode.INVALID_OTP);
        }

        UserEntity user;

        if (request.getIdentifier().contains("@")) {
            user = userRepository.findByEmail(request.getIdentifier())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        } else {
            user = userRepository.findByPhoneNumber(request.getIdentifier())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        otpService.deleteOtp(OtpType.PASSWORD_RESET, request.getIdentifier());
    }


    @Override
    public void changePassword(ChangePasswordRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new PasswordCannotMatchException(ExceptionCode.PASSWORD_CANNOT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        if (!jwtService.isTokenValid(request.getRefreshToken())) {
            throw new RefreshTokenInvalidException(ExceptionCode.REFRESH_TOKEN_INVALID);
        }

        Long userId = jwtService.extractUserId(request.getRefreshToken());
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .expiresIn(3600)
                .user(userMapper.toDto(user))
                .build();
    }

// bunu   usere kecirtmeyi unutma burda olmmali deyil
//    @Override
//    public UserDto getCurrentUser() {
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        UserEntity user = userRepository.findByUserName(username)
//                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
//
//        return userMapper.toDto(user);
//    }
}
