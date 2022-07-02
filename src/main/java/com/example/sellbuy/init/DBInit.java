package com.example.sellbuy.init;

import com.example.sellbuy.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;
    private final PictureService pictureService;
    private final CategoryService categoryService;
    private final LocationService locationService;

    public DBInit(UserService userService, ProductService productService,
                  PictureService pictureService,@Lazy CategoryService categoryService,
                  LocationService locationService) {

        this.userService = userService;
        this.productService = productService;
        this.pictureService = pictureService;
        this.categoryService = categoryService;
        this.locationService = locationService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.locationService.initializeLocations();
        this.userService.initializeUsersAndRoles();
        this.pictureService.initializePictures();
        this.categoryService.initializedCategories();
        this.productService.initializeProducts();
    }
}
