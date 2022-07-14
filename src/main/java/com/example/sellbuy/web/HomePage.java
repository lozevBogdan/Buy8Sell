package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomePage {

    @ModelAttribute
    public ProductSearchingBindingModel productSearchingBindingModel(){
        return new ProductSearchingBindingModel();
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

}
