package com.example.sellbuy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping
    public String loginPage(){
        return "loginPage";
    }


    @GetMapping("/register")
    public String registerPage(){
        return "registerPage";
    }

}
