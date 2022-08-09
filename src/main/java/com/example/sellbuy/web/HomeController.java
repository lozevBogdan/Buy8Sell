package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.exception.NotAuthorizedException;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @ModelAttribute
    public ProductSearchingBindingModel productSearchingBindingModel(){
        return new ProductSearchingBindingModel();
    }

    @GetMapping("/")
    public String home(Model model){

        List<ProductSearchViewModel> promotions = this.productService.getThreeRandomProducts();

        if (!model.containsAttribute("promotions")){
            model.addAttribute("promotions",promotions);
        }

        this.productService.removeExpiredProducts();

        return "index";
    }

    @ResponseStatus(value = HttpStatus.TOO_EARLY)
    @ExceptionHandler({IllegalArgumentException.class})
    public String notAuthorized () {

        return "error-products-not-loaded-yet";

    }




}
