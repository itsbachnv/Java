package com.foodapp.foodapp.UserService.service;

import com.foodapp.foodapp.UserService.entity.OtpToken;
import com.foodapp.foodapp.UserService.exception.AppExceptions.*;
import com.foodapp.foodapp.UserService.repository.OtpTokenRepository;
import com.foodapp.foodapp.common.Embeddable.OtpPurpose;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final OtpTokenRepository otpRepo;
    private final NotificationService notificationService;

    @Value("${app.otp.expiry-seconds:120}")
    private long expirySeconds;

    @Value("${app.otp.max-attempts:5}")
    private int maxAttempts;

    @Value("${app.otp.max-resend-per-day:5}")
    private int maxResendPerDay;

    @Value("${app.otp.rate-limit-per-ip:10}")
    private long rateLimitPerIp;

    private final SecureRandom random = new SecureRandom();

    /** Tạo và gửi OTP. Nếu OTP cũ còn hạn + chưa qua cooldown → báo chờ thêm */
    @Transactional
    public OtpToken createAndSend(String identifier, OtpPurpose purpose, String ip) {

        // 1. Rate limit IP
        if (ip != null) {
            long ipCount = otpRepo.countByIpAddressAndCreatedAtAfter(ip, LocalDateTime.now().minusHours(1));
            if (ipCount >= rateLimitPerIp) throw new RateLimitException();
        }

        // 2. Kiểm tra giới hạn resend trong ngày
        int resendToday = otpRepo.sumResendSince(identifier, purpose, LocalDate.now().atStartOfDay());
        if (resendToday >= maxResendPerDay) throw new OtpResendLimitException();

        // 3. OTP còn hạn → resend nếu qua cooldown, hoặc báo chờ
        otpRepo.findTopByIdentifierAndPurposeAndUsedFalseOrderByCreatedAtDesc(identifier, purpose)
                .ifPresent(existing -> {
                    if (!existing.isExpired()) {
                        if (!existing.canResend()) {
                            throw new OtpCooldownException(existing.secondsUntilCanResend());
                        }
                        // Qua cooldown → increment resend rồi gửi lại token cũ
                        existing.setResendCount(existing.getResendCount() + 1);
                        existing.setLastResendAt(LocalDateTime.now());
                        otpRepo.save(existing);
                        send(identifier, existing.getToken(), purpose);
                        log.info("[OTP] Resend to {} purpose={}", mask(identifier), purpose);
                        // Return sớm bằng cách throw ra ngoài để skip bước tạo mới
                        // Dùng RuntimeException wrapper để thoát khỏi lambda
                        throw new ResendDoneSignal(existing);
                    }
                });

        // 4. Invalidate OTP cũ → tạo mới
        otpRepo.invalidateAll(identifier, purpose);

        OtpToken otp = new OtpToken();
        otp.setIdentifier(identifier);
        otp.setToken(generateCode());
        otp.setPurpose(purpose);
        otp.setExpiresAt(LocalDateTime.now().plusSeconds(expirySeconds));
        otp.setIpAddress(ip);
        otpRepo.save(otp);

        send(identifier, otp.getToken(), purpose);
        log.info("[OTP] New OTP sent to {} purpose={}", mask(identifier), purpose);
        return otp;
    }

    /** Xử lý ResendDoneSignal: nếu bị ném ra → trả về OTP đã resend */
    @Transactional
    public OtpToken createAndSendSafe(String identifier, OtpPurpose purpose, String ip) {
        try {
            return createAndSend(identifier, purpose, ip);
        } catch (ResendDoneSignal s) {
            return s.getOtp();
        }
    }

    /** Xác thực OTP */
    @Transactional
    public void verify(String identifier, String input, OtpPurpose purpose) {
        OtpToken otp = otpRepo
                .findTopByIdentifierAndPurposeAndUsedFalseOrderByCreatedAtDesc(identifier, purpose)
                .orElseThrow(OtpExpiredException::new);

        if (otp.isExpired()) {
            otp.setUsed(true);
            otpRepo.save(otp);
            throw new OtpExpiredException();
        }

        if (otp.isMaxAttempts()) {
            throw new OtpCooldownException(300); // khóa 5 phút
        }

        if (!otp.getToken().equals(input)) {
            otp.setFailedAttempts(otp.getFailedAttempts() + 1);
            otpRepo.save(otp);
            throw new InvalidOtpException(maxAttempts - otp.getFailedAttempts());
        }

        otp.setUsed(true);
        otp.setVerifiedAt(LocalDateTime.now());
        otpRepo.save(otp);
    }

    // ── private ──────────────────────────────────────────────

    private void send(String identifier, String token, OtpPurpose purpose) {
        boolean isPhone = identifier.matches("^0[0-9]{9}$");
        String msg = "Mã OTP của bạn là: " + token + ". Có hiệu lực trong 2 phút.";
        if (isPhone) notificationService.sendSms(identifier, msg);
        else         notificationService.sendEmail(identifier, "Mã xác thực", msg);
    }

    private String generateCode() {
        return String.format("%06d", random.nextInt(1_000_000));
    }

    public static String mask(String s) {
        if (s == null || s.length() < 6) return "***";
        return s.substring(0, 3) + "***" + s.substring(s.length() - 3);
    }

    public long getExpirySeconds() { return expirySeconds; }

    /** Signal nội bộ để thoát khỏi lambda khi resend thành công */
    static class ResendDoneSignal extends RuntimeException {
        private final OtpToken otp;
        ResendDoneSignal(OtpToken otp) { super(null, null, true, false); this.otp = otp; }
        OtpToken getOtp() { return otp; }
    }
}