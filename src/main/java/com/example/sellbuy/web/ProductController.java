package com.example.sellbuy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/all")
    public String productsPage(){
        return "productsPage";
    }


    @GetMapping("/add")
    public String allProducts(){
        return "AddProductPage";
    }



}
