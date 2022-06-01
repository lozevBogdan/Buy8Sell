package com.example.sellbuy.init;

import com.example.sellbuy.service.PictureService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;
    private final PictureService pictureService;

    public DBInit(UserService userService, ProductService productService, PictureService pictureService) {
        this.userService = userService;
        this.productService = productService;
        this.pictureService = pictureService;
    }

    @Override
    public void run(String... args) throws Exception {

        userService.initializeUsersAndRoles();
        pictureService.initializePictures();
        productService.initializeProducts();

    }
}
