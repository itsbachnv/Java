package com.foodapp.foodapp.UserService.entity;

import com.foodapp.foodapp.common.Embeddable.OtpPurpose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "otp_tokens", indexes = {
        @Index(name = "idx_otp_identifier", columnList = "identifier")
})
public class OtpToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String identifier; // SĐT hoặc email

    @Column(nullable = false, length = 6)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpPurpose purpose; // REGISTER, RESET_PASSWORD, ...

    @Column(nullable = false)
    private Boolean used = false;

    @Column(nullable = false)
    private Integer failedAttempts = 0;

    @Column(nullable = false)
    private Integer resendCount = 0;

    private LocalDateTime lastResendAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime verifiedAt;

    @Column(length = 45)
    private String ipAddress;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ── helpers ──────────────────────────────────────────────

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isMaxAttempts() {
        return failedAttempts >= 5;
    }

    /** Cooldown 60s giữa các lần resend */
    public boolean canResend() {
        if (lastResendAt == null) return true;
        return LocalDateTime.now().isAfter(lastResendAt.plusSeconds(60));
    }

    public long secondsUntilCanResend() {
        if (canResend()) return 0;
        return java.time.temporal.ChronoUnit.SECONDS.between(
                LocalDateTime.now(), lastResendAt.plusSeconds(60));
    }
}