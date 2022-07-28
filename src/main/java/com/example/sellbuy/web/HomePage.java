package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomePage {

    private final ProductService productService;

    public HomePage(ProductService productService) {
        this.productService = productService;
    }

    @ModelAttribute
    public ProductSearchingBindingModel productSearchingBindingModel(){
        return new ProductSearchingBindingModel();
    }

    @GetMapping("/")
    public String home(Model model){

        List<ProductSearchViewModel> promotions = this.productService.getTreeRandomProducts();

        if (!model.containsAttribute("promotions")){
            model.addAttribute("promotions",promotions);
        }

        return "index";
    }

}
