package com.foodapp.foodapp.UserService.service;

import com.foodapp.foodapp.NotificationService.service.NotificationService;
import com.foodapp.foodapp.UserService.dto.RegistrationDto.*;
import com.foodapp.foodapp.UserService.entity.User;
import com.foodapp.foodapp.UserService.exception.AppExceptions.DuplicateException;
import com.foodapp.foodapp.UserService.exception.AppExceptions.PasswordMismatchException;
import com.foodapp.foodapp.UserService.exception.AppExceptions.UserNotFoundException;
import com.foodapp.foodapp.UserService.repository.UserRepository;
import com.foodapp.foodapp.common.Embeddable.OtpPurpose;
import com.foodapp.foodapp.common.Embeddable.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepo;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final NotificationService notificationService;
    private final PasswordEncoder passwordEncoder;

    private final SecureRandom random = new SecureRandom();

    /** BƯỚC 1 — Validate + tạo User PENDING + gửi OTP */
    @Transactional
    public OtpSentResponse register(RegisterRequest req, String ip) {

        // Validate password khớp
        if (!req.getPassword().equals(req.getConfirmPassword()))
            throw new PasswordMismatchException();

        // Kiểm tra trùng (chỉ báo lỗi nếu account đã ACTIVE)
        userRepo.findByEmail(req.getEmail()).ifPresent(u -> {
            if (u.getStatus() == UserStatus.ACTIVE) throw new DuplicateException("Email");
        });

        // Xóa tài khoản PENDING cũ nếu có (user đăng ký lại)
        userRepo.findByEmail(req.getEmail()).ifPresent(u -> {
            if (u.getStatus() == UserStatus.PENDING) userRepo.delete(u);
        });

        // Tạo User PENDING
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setStatus(UserStatus.PENDING);
        user.setReferralCode(generateReferralCode());
        user.setRegistrationExpiresAt(LocalDateTime.now().plusHours(24));

        // Gắn referral nếu hợp lệ
        if (StringUtils.hasText(req.getReferralCode())
                && userRepo.existsByReferralCode(req.getReferralCode())) {
            user.setReferredBy(req.getReferralCode());
        }

        userRepo.save(user);

        // Gửi OTP đến email (hoặc phone nếu có)
        String identifier = req.getEmail();
        otpService.createAndSendSafe(identifier, OtpPurpose.REGISTER, ip);

        return OtpSentResponse.builder()
                .maskedIdentifier(OtpService.mask(identifier))
                .message("OTP đã được gửi đến " + OtpService.mask(identifier))
                .otpExpiresInSeconds(otpService.getExpirySeconds())
                .cooldownSeconds(60)
                .build();
    }

    /** BƯỚC 2 — Verify OTP + activate + trả JWT */
    @Transactional
    public RegisterResponse verifyOtp(VerifyOtpRequest req) {

        // Xác thực OTP (throws nếu sai / hết hạn)
        otpService.verify(req.getIdentifier(), req.getOtp(), OtpPurpose.REGISTER);

        // Tìm user PENDING
        User user = userRepo.findByEmail(req.getIdentifier())
                .orElseThrow(UserNotFoundException::new);

        // Activate
        user.setStatus(UserStatus.ACTIVE);
        user.setEmailVerified(true);
        user.setRegistrationExpiresAt(null);
        userRepo.save(user);

        // Thưởng referral nếu có
        applyReferral(user);

        // Welcome notification (async)
        notificationService.sendWelcome(user);

        log.info("[REGISTER] User {} activated", user.getId());

        return RegisterResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .expiresIn(jwtService.getAccessTokenExpiry())
                .user(toSummary(user))
                .build();
    }

    /** Resend OTP */
    public OtpSentResponse resendOtp(ResendOtpRequest req, String ip) {
        otpService.createAndSendSafe(req.getIdentifier(), OtpPurpose.REGISTER, ip);
        return OtpSentResponse.builder()
                .maskedIdentifier(OtpService.mask(req.getIdentifier()))
                .message("OTP đã được gửi lại")
                .otpExpiresInSeconds(otpService.getExpirySeconds())
                .cooldownSeconds(60)
                .build();
    }

    // ── private ──────────────────────────────────────────────

    private void applyReferral(User user) {
        if (!StringUtils.hasText(user.getReferredBy())) return;
        userRepo.findByReferralCode(user.getReferredBy()).ifPresent(referrer -> {
            // TODO: cộng điểm / voucher cho referrer
            log.info("[REFERRAL] {} referred by {}", user.getId(), referrer.getId());
        });
    }

    private String generateReferralCode() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        String code;
        do {
            StringBuilder sb = new StringBuilder(8);
            for (int i = 0; i < 8; i++)
                sb.append(chars.charAt(random.nextInt(chars.length())));
            code = sb.toString();
        } while (userRepo.existsByReferralCode(code));
        return code;
    }

    private UserSummary toSummary(User u) {
        return UserSummary.builder()
                .id(u.getId())
                .email(u.getEmail())
                .phone(u.getPhone())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .referralCode(u.getReferralCode())
                .build();
    }
}