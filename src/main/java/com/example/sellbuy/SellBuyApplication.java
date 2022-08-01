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


//todo: IMPORTANT: Проверка дали някой може да изтрие нещо на няккой друг потребител, чрез манупулация на заявка.
//todo: IMPORTANT: implement REST fetch - almost done

//todo: IMPORTANT: implement somewhere Exception handling (error handling)
//   done     idea 1 - exeption for unauthorized user, try to edit product
//      done     idea 2 -  error exception in case we wont to product with not exist id in DB - PRODUCT NOT FOUND EXCEPTION.

//todo: IMPORTANT: intercetor

//todo: IMPORTANT: implement Event -
//                  idea1- listener for getting product info -> increase a views with listener
//         idea2 - listeners for initializing DB. - DONE!

// DONE todo: IMPORTANT: implement Schedule  -
//                  idea1- schedule for products which not updated more than 30 days - DONE.
//          idea2- rotate in every hour a different products on index page + Cache. - DONE!

//todo: MAYBE add a new comments for approved by Admin, before their visualisation.


    }

}
