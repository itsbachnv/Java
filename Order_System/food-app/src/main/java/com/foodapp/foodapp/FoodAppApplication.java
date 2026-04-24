package com.foodapp.foodapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication
public class FoodAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodAppApplication.class, args);
    }

}
