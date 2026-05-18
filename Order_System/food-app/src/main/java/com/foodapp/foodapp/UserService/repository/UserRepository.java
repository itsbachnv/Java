package com.foodapp.foodapp.UserService.repository;

import com.foodapp.foodapp.UserService.entity.User;
import com.foodapp.foodapp.common.Embeddable.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByReferralCode(String referralCode);

    Optional<User> findByReferralCode(String referralCode);

    // Xóa tài khoản PENDING quá hạn (chạy bởi scheduler)
    @Modifying
    @Query("DELETE FROM User u WHERE u.status = :status AND u.registrationExpiresAt < :now")
    int deleteExpiredByStatus(@Param("status") UserStatus status,
                              @Param("now") LocalDateTime now);
}