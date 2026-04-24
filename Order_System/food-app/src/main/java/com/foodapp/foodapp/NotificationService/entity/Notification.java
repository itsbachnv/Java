package com.foodapp.foodapp.NotificationService.entity;

import com.foodapp.foodapp.common.Embeddable.NotificationChannel;
import com.foodapp.foodapp.common.Embeddable.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private String userId;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    // ORDER_CONFIRMED, PAYMENT_SUCCESS, SHIPPED, DELIVERED, PROMOTION

    private String title, message;
    private boolean isRead;
    private Map<String, Object> metadata; // orderId, trackingCode...

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel; // PUSH, EMAIL, SMS

    private LocalDateTime createdAt, readAt;
}