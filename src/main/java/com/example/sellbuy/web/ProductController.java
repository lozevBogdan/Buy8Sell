package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.CommentBindingDto;
import com.example.sellbuy.model.binding.MessageBindingModel;
import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.view.productViews.ProductDetailsViewDto;
import com.example.sellbuy.model.view.productViews.ProductEditViewModel;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.service.PictureService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PictureService pictureService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, PictureService pictureService, UserService userService, ModelMapper modelMapper) {
        this.productService = productService;
        this.pictureService = pictureService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public CommentBindingDto commentBindingDto(){
        return new CommentBindingDto();
    }

    @ModelAttribute
    public ProductAddBindingModel productAddBindingModel(){
        return new ProductAddBindingModel();
    }

    @ModelAttribute
    public MessageBindingModel messageBindingModel(){
        return new MessageBindingModel();
    }

    @ModelAttribute
    public ProductSearchingBindingModel productSearchingBindingModel(){
        return new ProductSearchingBindingModel();
    }

    @GetMapping("/all")
    public String productsPage(Model model,
                               @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(new ProductSearchingBindingModel(), sellAndBuyUser.getId(),false);
        if (!model.containsAttribute("productSearchViewModelList")){
            model.addAttribute("productSearchViewModelList",productSearchViewModelList);
        }
        return "products-all";
    }

    @GetMapping("/all/promotion")
    public String allPromotions(Model model,
                               @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(new ProductSearchingBindingModel(), sellAndBuyUser.getId(),true);
        if (!model.containsAttribute("productSearchViewModelList")){
            model.addAttribute("productSearchViewModelList",productSearchViewModelList);
            model.addAttribute("noResults",productSearchViewModelList.size()==0);
        }
        return "products-promotions";
    }

    @PostMapping("/all/promotion")
    public String allPromotions(@Valid ProductSearchingBindingModel productSearchingBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Model model, @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser,
                      @RequestParam(value = "category",required = false) String category){

        boolean isMinBiggerThanMax = false;

        if (category != ""){
            productSearchingBindingModel.setCategory(CategoryEnum.valueOf(category));
        }

        if (productSearchingBindingModel.getMin() != null && productSearchingBindingModel.getMax() != null) {
            isMinBiggerThanMax =
                    productSearchingBindingModel.getMin() > productSearchingBindingModel.getMax();
        }

        if (bindingResult.hasErrors() || isMinBiggerThanMax) {
            redirectAttributes.addFlashAttribute("productSearchingBindingModel", productSearchingBindingModel);
            redirectAttributes.addFlashAttribute("isMinBiggerThanMax", isMinBiggerThanMax);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productSearchingBindingModel", bindingResult);

            return "redirect:/products/all/promotion";
        }
        List<ProductSearchViewModel> productSearchViewModelList =
                this.productService.filterBy(productSearchingBindingModel,sellAndBuyUser.getId(),true);
        model.addAttribute("productSearchViewModelList", productSearchViewModelList);
        model.addAttribute("noResults",productSearchViewModelList.size()==0);

        return "products-promotions";
    }

    @GetMapping("/add")
    public String allProducts(){
        return "product-add";
    }

//todo:make a delete post !!!!!!!
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        this.productService.deleteProductById(id);
        return String.format("redirect:/users/%d/products",sellAndBuyUser.getId());
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id,Model model){

        ProductEditViewModel productEditViewModel =
                this.productService.findByIdProductSearchAndEditViewModel(id);
        if(!model.containsAttribute("productEditViewModel")){
            model.addAttribute("productEditViewModel",productEditViewModel);
        }
        return "product-edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @RequestParam(defaultValue = "false") boolean isPromo,
                              @Valid ProductEditViewModel productEditViewModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){

         productEditViewModel.setPromo(isPromo);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productEditViewModel", productEditViewModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productEditViewModel",
                    bindingResult);
            return "redirect:/products/edit/" + id;
        }
       ProductEntity updatedProduct =
               this.productService.updateProductById(id,productEditViewModel);
        redirectAttributes.addFlashAttribute("updated", true);
        return "redirect:/products/info/" + id;
    }

    @GetMapping("/info/{id}")
    public String productInfo(@PathVariable Long id,Model model,
                              @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        ProductDetailsViewDto productInfoView = this.productService.getAndIncreaseViewsProductById(id);

       if(productService.isConsist(this.userService.findById(sellAndBuyUser.getId()).
                       getFavoriteProducts(),productInfoView)) {
           productInfoView.setProductIsFavorInCurrentUser(true);
       }
        model.addAttribute("productInfoView",productInfoView);
       //todo: include modified data!!!
        model.addAttribute("created",productInfoView.getCreated());
        return "product-Info";
    }

    @PostMapping("/add")
    public String add(@RequestParam(defaultValue = "false") boolean isPromo,
                      @Valid ProductAddBindingModel productAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        //todo: to check for null picture and if null to set some default !!!!!
        productAddBindingModel.setPromo(isPromo);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:/products/add";
        }

        ProductEntity newProduct =
                this.productService.addProductBindingModel(productAddBindingModel,sellAndBuyUser);

        return String.format("redirect:/users/%d/products",
               sellAndBuyUser.getId());
    }

    @PostMapping("/all")
    public String all(@Valid ProductSearchingBindingModel productSearchingBindingModel,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model, @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser,
                      @RequestParam(value = "category",required = false) String category){

        boolean isMinBiggerThanMax = false;

        if (category != ""){
            productSearchingBindingModel.setCategory(CategoryEnum.valueOf(category));
        }

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
                this.productService.filterBy(productSearchingBindingModel,sellAndBuyUser.getId(),false);
        model.addAttribute("productSearchViewModelList", productSearchViewModelList);
        model.addAttribute("noResults",productSearchViewModelList.size()==0);
        return "products-all";
    }

    //EXAMPLE FOR REQUEST PARAM

//    @GetMapping
//    public String indexRedirect(
//            @RequestParam(value = "category",required = false) String category, RedirectAttributes redirectAttributes){
//
//        CategoryEnum categoryEnum = null;
//
//        switch (category){
//            case "HOUSEHOLD":
//                categoryEnum = CategoryEnum.HOUSEHOLD;
//            case "FASHION":
//                categoryEnum = CategoryEnum.FASHION;
//            case "SERVICES":
//                categoryEnum = CategoryEnum.SERVICES;
//            case "PROPERTY":
//                categoryEnum = CategoryEnum.PROPERTIES;
//            case "ELECTRONICS":
//                categoryEnum = CategoryEnum.ELECTRONICS;
//            case "VEHICLES":
//                categoryEnum = CategoryEnum.VEHICLES;
//            case "ANIMALS":
//                categoryEnum = CategoryEnum.ANIMALS;
//            case "OTHERS":
//                categoryEnum = CategoryEnum.OTHER;
//        }
//
//        ProductSearchingBindingModel productSearchingBindingModel = new ProductSearchingBindingModel();
//        productSearchingBindingModel.setCategory(categoryEnum);
//
//        redirectAttributes.addFlashAttribute("productSearchingBindingModel", productSearchingBindingModel);
//
//        return "redirect:/products/all";
//    }



}
