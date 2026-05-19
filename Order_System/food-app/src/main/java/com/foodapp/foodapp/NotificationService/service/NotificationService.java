package com.foodapp.foodapp.NotificationService.service;

import com.foodapp.foodapp.UserService.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:noreply@foodapp.vn}")
    private String mailFrom;

    @Value("${app.sms.enabled:false}")
    private boolean smsEnabled;

    @Async
    public void sendSms(String phoneNumber, String message) {
        if (!smsEnabled) {
            log.info("[SMS-MOCK] To: {} | Msg: {}", phoneNumber, message);
            return;
        }
        // TODO: tích hợp SMS gateway thực (Twilio, VNPT, Viettel...)
        log.info("[SMS] Sent to {}", phoneNumber);
    }

    @Async
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(mailFrom);
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            log.info("[EMAIL] Sent to {}", to);
        } catch (Exception e) {
            // Không throw để không block main flow
            log.error("[EMAIL] Failed to send to {}: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendWelcome(User user) {
        String name = user.getFirstName() != null ? user.getFirstName() : "bạn";
        if (user.getEmail() != null) {
            sendEmail(
                    user.getEmail(),
                    "Chào mừng đến với FoodApp!",
                    "Xin chào " + name + ",\n\n" +
                            "Tài khoản của bạn đã được tạo thành công.\n" +
                            "Mã giới thiệu của bạn: " + user.getReferralCode() + "\n\n" +
                            "Chúc bạn ngon miệng!\nFoodApp Team"
            );
        } else if (user.getPhone() != null) {
            sendSms(user.getPhone(),
                    "Chao mung den voi FoodApp! Ma gioi thieu: " + user.getReferralCode());
        }
    }
}