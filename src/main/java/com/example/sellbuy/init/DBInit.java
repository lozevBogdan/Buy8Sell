package com.example.sellbuy.init;

import com.example.sellbuy.service.CategoryService;
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
    private final CategoryService categoryService;

    public DBInit(UserService userService, ProductService productService, PictureService pictureService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.pictureService = pictureService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.userService.initializeUsersAndRoles();
        this.pictureService.initializePictures();
        this.productService.initializeProducts();
        this.categoryService.initializedCategories();

    }
}
