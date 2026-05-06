package com.foodapp.foodapp;

import com.foodapp.foodapp.NotificationService.entity.Notification;
import com.foodapp.foodapp.NotificationService.repository.NotificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FoodAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodAppApplication.class, args);
    }
    @Bean
    CommandLineRunner initMongo(NotificationRepository notificationRepository) {
        return args -> {
            Notification n = new Notification();
            n.setUserId("user-001");
            n.setTitle("Chào mừng!");
            n.setMessage("Đơn hàng của bạn đã được xác nhận");
            n.setRead(false);
            //notificationRepository.save(n);

            //System.out.println("✅ MongoDB insert thành công!");
        };
    }
}
