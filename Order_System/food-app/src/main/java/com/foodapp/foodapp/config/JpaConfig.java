package com.foodapp.foodapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = {
        "com.foodapp.foodapp.UserService.repository",
        "com.foodapp.foodapp.OrderService.repository",
        "com.foodapp.foodapp.ProductService.repository",
        "com.foodapp.foodapp.InventoryService.repository",
        "com.foodapp.foodapp.PaymentService.repository",
        "com.foodapp.foodapp.ShippingService.repository"
})
public class JpaConfig {
}