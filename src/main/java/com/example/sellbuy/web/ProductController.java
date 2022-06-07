package com.example.sellbuy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping
    public String productsPage(){
        return "productsPage";
    }



}
