package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;


    public UserController(UserService userService, ProductService productService, ModelMapper modelMapper) {
        this.userService = userService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


// because spring security
//    @GetMapping("/logout")
//    public String logout(){
//        this.userService.logoutCurrentUser();
//        return "redirect:/";
//    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/{id}/favorites")
    public String getAllFavorites(@PathVariable Long id, Model model){

        Set<ProductEntity> favorList = this.userService.getFavorListOf(id);

        List<ProductSearchViewModel> productSearchViewModelList =
              this.returnFavors(favorList,id);

        if (!model.containsAttribute("productSearchViewModelList")){
            model.addAttribute("productSearchViewModelList",productSearchViewModelList);
        }
            return "favorites-products";

    }

    @GetMapping("/{id}/products")
    public String myProducts(@PathVariable Long id, Model model){

        Set<ProductEntity> myProducts = this.productService.findProductsByUserId(id);

//        Set<ProductEntity> myProducts = this.userService.getMyProductsById(id);

        List<ProductSearchViewModel> myProductsSearchViewModelList =
                this.returnFavors(myProducts,id);

        if (!model.containsAttribute("myProductsSearchViewModelList")){
            model.addAttribute("myProductsSearchViewModelList",myProductsSearchViewModelList);
        }
        return "my-products";

    }

    private  List<ProductSearchViewModel> returnFavors(Set<ProductEntity> favorProducts, Long userId){

        List<ProductSearchViewModel> returnedList = new LinkedList<>();

        for (ProductEntity product : favorProducts) {

            ProductSearchViewModel productSearchViewModel =
                    this.modelMapper.map(product, ProductSearchViewModel.class);

            productSearchViewModel.setMainPicture(product.getPicture().getUrl());

            UserEntity currentLoggedInUserEntity =
                    this.userService.getCurrentLoggedInUserEntityById(userId);

            // Check for favorites products for current user
            if(currentLoggedInUserEntity != null){

                Set<ProductEntity> favoriteProducts =
                        currentLoggedInUserEntity.getFavoriteProducts();

                if(this.productService.isConsist(favoriteProducts,product)){
                    productSearchViewModel.setProductIsFavorInCurrentUser(true);
                    System.out.println();
                }
            }
            returnedList.add(productSearchViewModel);
        }
            return returnedList;
    }


    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        boolean isEmailFree =
                this.userService.isEmailFree(userRegisterBindingModel.getEmail());

        boolean passwordsAreEquals = userRegisterBindingModel.getPassword().
                equals(userRegisterBindingModel.getConfirmPassword());

        if (bindingResult.hasErrors() || !isEmailFree || !passwordsAreEquals) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("emailIsNotFree", !isEmailFree);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute(
                    "passwordsNotMach",!passwordsAreEquals);
            return "redirect:/users/register";
        }
        userService.makeNewRegistration(userRegisterBindingModel);
        return "redirect:login";
    }

//                      because spring security
//    @PostMapping("/login")
//    public String login(@Valid UserLoginBindingModel userLoginBindingModel,
//                           BindingResult bindingResult,
//                           RedirectAttributes redirectAttributes){
//
//            boolean isCredentialsAreValid =
//                    this.userService.
//                            isExistUserWithEmailAndPassword(userLoginBindingModel.getEmail(),
//                                    userLoginBindingModel.getPassword());
//
//        if (bindingResult.hasErrors() || isCredentialsAreValid) {
//            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
//            redirectAttributes.addFlashAttribute(
//                    "org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
//            redirectAttributes.addFlashAttribute("invalidCredentials", isCredentialsAreValid);
//
//            return "redirect:/users/login";
//        }
//
//        userService.loginUser(userLoginBindingModel);
//
//        return "redirect:/";
//    }

    @PostMapping("/add/favorites/{id}")
    public String addFavorites(@PathVariable Long id,
                               @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntityById(sellAndBuyUser.getId());

        if(currentUser == null){
            return "redirect:/users/login";
        }else {

            ProductEntity product = this.productService.findById(id);
            product.getFans().add(currentUser);
            this.productService.addProductEntity(product);

            currentUser.getFavoriteProducts().add(product);
            currentUser = this.userService.addInDb(currentUser);
            System.out.println();
            return "redirect:/products/all";
        }
    }

    @PostMapping("/add/favorites/{id}/info")
    public String addFavoritesAndRedirectToInfoProductPage(@PathVariable Long id,
                               @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntityById(sellAndBuyUser.getId());

        if(currentUser == null){
            return "redirect:/users/login";
        }else {

            ProductEntity product = this.productService.findById(id);
            product.getFans().add(currentUser);
            this.productService.addProductEntity(product);

            currentUser.getFavoriteProducts().add(product);
            currentUser = this.userService.addInDb(currentUser);
            System.out.println();
            return  String.format("redirect:/products/info/%d",id);
        }
    }

    @PostMapping("/remove/favorites/{id}")
    public String removeFromFavorites(@PathVariable Long id,
                                      @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntityById(sellAndBuyUser.getId());

        if(currentUser == null){
            return "redirect:/users/login";
        }else {

            ProductEntity product = this.productService.findById(id);
            product.getFans().remove(currentUser);
            this.productService.addProductEntity(product);
            //  this.userService.addFavorProduct(product);
            currentUser.getFavoriteProducts().remove(product);

            currentUser = this.userService.addInDb(currentUser);
            return String.format("redirect:/users/%d/favorites",currentUser.getId());
        }
    }

    @PostMapping("/remove/favorites/{id}/all")
    public String removeFromFavoritesAndRedirectToAll(@PathVariable Long id,
                                      @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntityById(sellAndBuyUser.getId());

        if(currentUser == null){
            return "redirect:/users/login";
        }else {
            ProductEntity product = this.productService.findById(id);
            product.getFans().remove(currentUser);
            this.productService.addProductEntity(product);
            //  this.userService.addFavorProduct(product);
            currentUser.getFavoriteProducts().remove(product);

            currentUser = this.userService.addInDb(currentUser);
            return "redirect:/products/all";
        }
    }

    @PostMapping("/remove/favorites/{id}/info")
    public String removeFromFavoritesAndRedirectToInfoPageProduct(@PathVariable Long id,
                                                      @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntityById(sellAndBuyUser.getId());

        if(currentUser == null){
            return "redirect:/users/login";
        }else {
            ProductEntity product = this.productService.findById(id);
            product.getFans().remove(currentUser);
            this.productService.addProductEntity(product);
            currentUser.getFavoriteProducts().remove(product);

            currentUser = this.userService.addInDb(currentUser);
            return String.format("redirect:/products/info/%d",id);
        }
    }



}
