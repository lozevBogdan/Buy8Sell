package com.example.sellbuy.web;

import com.example.sellbuy.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeleteFirstProductController {

    private final ProductService productService;

    public DeleteFirstProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/deleteFirstProduct")
    public String deleteFirstProduct(){

        System.out.println("Im in delete Controller!!!!!");
        productService.deleteFistProduct();
        return "isdeleted";
    }

}
