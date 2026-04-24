package com.foodapp.foodapp.common.Embeddable;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
@Embeddable

public class ReviewReply {
    private String sellerId;
    private String content;
    private LocalDateTime repliedAt;
}