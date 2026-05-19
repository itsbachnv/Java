package com.foodapp.foodapp.UserService.scheduler;

import com.foodapp.foodapp.UserService.repository.OtpTokenRepository;
import com.foodapp.foodapp.UserService.repository.UserRepository;
import com.foodapp.foodapp.common.Embeddable.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationCleanupJob {

    private final UserRepository userRepo;
    private final OtpTokenRepository otpRepo;

    /** Mỗi giờ: xóa User PENDING quá hạn 24h */
    @Scheduled(fixedRate = 3_600_000)
    @Transactional
    public void cleanupPendingUsers() {
        int n = userRepo.deleteExpiredByStatus(UserStatus.PENDING, LocalDateTime.now());
        if (n > 0) log.info("[CLEANUP] Deleted {} expired PENDING users", n);
    }

    /** Mỗi 30 phút: xóa OTP hết hạn */
    @Scheduled(fixedRate = 1_800_000)
    @Transactional
    public void cleanupExpiredOtps() {
        int n = otpRepo.deleteExpired(LocalDateTime.now());
        if (n > 0) log.info("[CLEANUP] Deleted {} expired OTPs", n);
    }
}