package com.example.sellbuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.reflect.Field;

@SpringBootApplication
@EnableScheduling
public class SellBuyApplication {

    public static void main(String[] args) {

        SpringApplication.run(SellBuyApplication.class, args);

//todo: IMPORTANT: shedule
//todo: IMPORTANT: Проверка дали някой може да изтрие нещо на няккой друг потребител, чрез манупулация на заявка.
//todo: IMPORTANT: implement REST fetch - almost done
//todo: IMPORTANT: implement somewhere Exception handling (error handling)
//todo: IMPORTANT: intercetor
//todo: IMPORTANT: implement Event -
//                  idea1- listener for getting product info -> increase a views with listener
//                  idea2 - listeners for initializing DB.
//        todo: IMPORTANT: implement Schedule -
//                  idea1- schedule for products which not updated more than 30 days.
//                  idea2- rotate in every hour a different products on index page.

//todo: error exception in case we wont to product with not exist id in DB.
//todo: MAYBE add a new comments for approved by Admin, before their visualisation.


    }

}
