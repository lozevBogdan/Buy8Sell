package com.example.sellbuy.init;

import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;

    public DBInit(UserService userService, ProductService productService) {
        this.userService = userService;

        this.productService = productService;
    }


    @Override
    public void run(String... args) throws Exception {

      userService.initializeUsers();
      productService.initializeProductsAndPictures();



    }
}
