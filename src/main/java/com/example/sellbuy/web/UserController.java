package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.view.ProductSearchViewModel;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
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

    @GetMapping("/logout")
    public String logout(){
        this.userService.logoutCurrentUser();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/messages")
    public String messages(){
    return "chats-all";
    }


    @GetMapping("/{id}/favorites")
    public String getAllFavorites(@PathVariable Long id, Model model){

        Set<ProductEntity> favorList = this.userService.getFavorListOf(id);


        List<ProductSearchViewModel> productSearchViewModelList =
              this.returnFavors(favorList);

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
                this.returnFavors(myProducts);

        if (!model.containsAttribute("myProductsSearchViewModelList")){
            model.addAttribute("myProductsSearchViewModelList",myProductsSearchViewModelList);
        }
        return "my-products";

    }

    private  List<ProductSearchViewModel> returnFavors(Set<ProductEntity> favorProducts){

        List<ProductSearchViewModel> returnedList = new LinkedList<>();

        for (ProductEntity product : favorProducts) {

            ProductSearchViewModel productSearchViewModel =
                    this.modelMapper.map(product, ProductSearchViewModel.class);

            String pictureUrl;

            if(product.getPictures().size() == 0){
                pictureUrl="https://main.admin.forth.gr/files/site/no-image.png";
            }else {
                pictureUrl = product.getPictures().stream().findFirst().get().getUrl();
            }
            productSearchViewModel.setMainPicture(pictureUrl);
            UserEntity currentLoggedInUserEntity = this.userService.getCurrentLoggedInUserEntity();

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

//todo thi shoud be replece with Spring security propurties

//        if(this.userService.isUserLoggedIn()){
//            return "redirect:home";
//        }

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

    @PostMapping("/login")
    public String login(@Valid UserLoginBindingModel userLoginBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

            boolean isCredentialsAreValid =
                    this.userService.
                            isExistUserWithEmailAndPassword(userLoginBindingModel.getEmail(),
                                    userLoginBindingModel.getPassword());

        if (bindingResult.hasErrors() || isCredentialsAreValid) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("invalidCredentials", isCredentialsAreValid);

            return "redirect:/users/login";
        }

        userService.loginUser(userLoginBindingModel);

        return "redirect:/";
    }

    @PostMapping("/add/favorites/{id}")
    public String addFavorites(@PathVariable Long id){

        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntity();

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

    @PostMapping("/remove/favorites/{id}")
    public String removeFromFavorites(@PathVariable Long id){

        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntity();

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



}
