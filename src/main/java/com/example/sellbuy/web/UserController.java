package com.example.sellbuy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/users/login")
    public String loginPage(){
        return "login";
    }


    @GetMapping("/users/register")
    public String registerPage(){
        return "register";
    }

    @GetMapping("/users/favorites")
    public String favorites(){
        return "favorites-products";
    }

    @GetMapping("/users/messages")
    public String messages(){
        return "chats-all";
    }

}
