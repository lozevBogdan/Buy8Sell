package com.example.sellbuy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @GetMapping("/users/login")
    public String loginPage(){
        return "loginPage";
    }


    @GetMapping("/users/register")
    public String registerPage(){
        return "registerPage2";
    }

    @GetMapping("/users/favorites")
    public String favorites(){
        return "favorites";
    }

    @GetMapping("/users/messages")
    public String messages(){
        return "messages";
    }

}
