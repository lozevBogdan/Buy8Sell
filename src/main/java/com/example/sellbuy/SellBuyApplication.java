package com.example.sellbuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.reflect.Field;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class SellBuyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellBuyApplication.class, args);
    }

}
