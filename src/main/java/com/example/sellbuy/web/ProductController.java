package com.example.sellbuy.web;

import com.example.sellbuy.dto.AddProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/all")
    public String productsPage(){
        return "all-products";
    }


    @GetMapping("/add")
    public String allProducts(){
        return "add-new-product";
    }

    @PostMapping("/add")
    public String allProducts(AddProductDto addProductDto){

        System.out.println(addProductDto);
        return "add-new-product";
    }



}
