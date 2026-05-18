package com.foodapp.foodapp.UserService.repository;

import com.foodapp.foodapp.UserService.entity.OtpToken;
import com.foodapp.foodapp.common.Embeddable.OtpPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {

    // Lấy OTP active mới nhất
    Optional<OtpToken> findTopByIdentifierAndPurposeAndUsedFalseOrderByCreatedAtDesc(
            String identifier, OtpPurpose purpose);

    // Đánh dấu tất cả OTP cũ là đã dùng trước khi tạo mới
    @Modifying
    @Query("UPDATE OtpToken o SET o.used = true " +
            "WHERE o.identifier = :identifier AND o.purpose = :purpose AND o.used = false")
    int invalidateAll(@Param("identifier") String identifier,
                      @Param("purpose") OtpPurpose purpose);

    // Tổng số lần resend trong ngày
    @Query("SELECT COALESCE(SUM(o.resendCount), 0) FROM OtpToken o " +
            "WHERE o.identifier = :identifier AND o.purpose = :purpose " +
            "AND o.createdAt > :since")
    int sumResendSince(@Param("identifier") String identifier,
                       @Param("purpose") OtpPurpose purpose,
                       @Param("since") LocalDateTime since);

    // Rate limit theo IP
    long countByIpAddressAndCreatedAtAfter(String ipAddress, LocalDateTime after);

    // Cleanup OTP hết hạn
    @Modifying
    @Query("DELETE FROM OtpToken o WHERE o.expiresAt < :now AND o.used = false")
    int deleteExpired(@Param("now") LocalDateTime now);
}