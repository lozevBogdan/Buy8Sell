package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.ProductSearchViewModel;
import com.example.sellbuy.service.PictureService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PictureService pictureService;
    private final UserService userService;

    public ProductController(ProductService productService, PictureService pictureService, UserService userService) {
        this.productService = productService;
        this.pictureService = pictureService;
        this.userService = userService;
    }
    @ModelAttribute
    public ProductAddBindingModel productAddBindingModel(){
        return new ProductAddBindingModel();
    }

    @ModelAttribute
    public ProductSearchingBindingModel productSearchingBindingModel(){
        return new ProductSearchingBindingModel();
    }

//    @ModelAttribute
//    public List<ProductSearchViewModel> productSearchViewModelList(){
//        return new LinkedList<>();
//    }

    @GetMapping("/all")
    public String productsPage(Model model){

        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(new ProductSearchingBindingModel());

        if (!model.containsAttribute("productSearchViewModelList")){
            model.addAttribute("productSearchViewModelList",productSearchViewModelList);
        }
        return "all-products";
    }


    @GetMapping("/add")
    public String allProducts(){
        return "add-new-product";
    }


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        this.productService.deleteProductById(id);
        return String.format("redirect:/users/%d/products",
                this.userService.getCurrentLoggedInUserEntity().getId());
    }


    @PostMapping("/edit/{id}")
    public String addProduct(@PathVariable Long id){
        //todo
        return "EditPage";
    }


    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "false") boolean isPromo,
                        @Valid ProductAddBindingModel productAddBindingModel,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){

        productAddBindingModel.setPromo(isPromo);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:/products/add";
        }

        ProductEntity newProduct =
                this.productService.addProductBindingModel(productAddBindingModel);

        return String.format("redirect:/users/%d/products",
                userService.getCurrentLoggedInUserEntity().getId());
    }

    @PostMapping("/all")
    public String all(@Valid ProductSearchingBindingModel productSearchingBindingModel,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model){

        boolean isMinBiggerThanMax = false;

        if (productSearchingBindingModel.getMin() != null && productSearchingBindingModel.getMax() != null) {

            isMinBiggerThanMax =
                    productSearchingBindingModel.getMin() > productSearchingBindingModel.getMax();
        }

        if (bindingResult.hasErrors() || isMinBiggerThanMax) {
            redirectAttributes.addFlashAttribute("productSearchingBindingModel", productSearchingBindingModel);
            redirectAttributes.addFlashAttribute("isMinBiggerThanMax", isMinBiggerThanMax);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productSearchingBindingModel", bindingResult);

            return "redirect:/products/all";
        }

        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(productSearchingBindingModel);

        model.addAttribute("productSearchViewModelList", productSearchViewModelList);

        return "all-products";
    }



}
