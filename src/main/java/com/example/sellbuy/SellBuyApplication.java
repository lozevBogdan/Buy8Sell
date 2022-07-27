package com.example.sellbuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;

@SpringBootApplication
public class SellBuyApplication {

    public static void main(String[] args) {

        SpringApplication.run(SellBuyApplication.class, args);

//todo: IMPORTANT: implement REST fetch - almost done
//todo: IMPORTANT: implement somewhere Exception handling (error handling)
//todo: IMPORTANT: intercetor
//todo: IMPORTANT: shedule
//todo: IMPORTANT: implement Event
//todo: error exception in case we wont to product with not exist id in DB.
//todo: MAYBE add a new comments for approved by Admin, before their visualisation.


    }

}
