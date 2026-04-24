package com.foodapp.foodapp.NotificationService.entity;

import com.foodapp.foodapp.common.Embeddable.EmailStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "email_logs")
public class EmailLog {
    @Id
    private String id;
    private String to, subject, templateName;
    private Map<String, Object> variables;
    @Enumerated(EnumType.STRING)
    private EmailStatus status; // PENDING, SENT, FAILED
    private String errorMessage;
    private LocalDateTime sentAt;
}