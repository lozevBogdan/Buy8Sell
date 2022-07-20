package com.example.sellbuy.web;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserProductsController {

    private final UserService userService;
    private final ProductService productService;

    public UserProductsController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/{id}/products")
    public String myProducts(@PathVariable Long id, Model model) {

        Set<ProductEntity> myProducts = this.productService.findProductsByUserId(id);
        List<ProductSearchViewModel> myProductsSearchViewModelList =
                this.userService.returnFavors(myProducts, id);

        if (!model.containsAttribute("myProductsSearchViewModelList")) {
            model.addAttribute("myProductsSearchViewModelList", myProductsSearchViewModelList);
        }
        return "my-products";
    }

    @GetMapping("/{id}/favorites")
    public String getAllFavorites(@PathVariable Long id, Model model) {

        Set<ProductEntity> favorList = this.userService.getFavorListOf(id);

        List<ProductSearchViewModel> productSearchViewModelList =
                this.userService.returnFavors(favorList, id);

        if (!model.containsAttribute("productSearchViewModelList")) {
            model.addAttribute("productSearchViewModelList", productSearchViewModelList);
        }
        return "products-favorites";
    }

    @PostMapping("/add/favorites/{id}/{previousPage}")
    public String addFavorites(@PathVariable Long id,
                               @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser,
                               @PathVariable String previousPage) {

        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntityById(sellAndBuyUser.getId());

        if (currentUser == null) {
            return "redirect:/users/login";
        } else {
            ProductEntity product = this.productService.findById(id);
            product.getFans().add(currentUser);
            this.productService.addProductEntity(product);

            currentUser.getFavoriteProducts().add(product);
            currentUser = this.userService.addInDb(currentUser);

            String redirectPage = "";

            if (previousPage.equals("promotion")) {
                redirectPage = "redirect:/products/all/promotion";
            } else if (previousPage.equals("all")) {
                redirectPage = "redirect:/products/all";
            } else if (previousPage.equals("favorites")) {
                redirectPage = String.format("redirect:/users/%d/favorites", currentUser.getId());
            } else if (previousPage.equals("info")) {
                redirectPage = String.format("redirect:/products/info/%d", id);
            }
            return redirectPage;
        }
    }


    @PostMapping("/remove/favorites/{id}/{previousPage}")
    public String removeFromFavorites(@PathVariable Long id,
                                      @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser,
                                      @PathVariable String previousPage) {
        UserEntity currentUser = this.userService.getCurrentLoggedInUserEntityById(sellAndBuyUser.getId());
        if (currentUser == null) {
            return "redirect:/users/login";
        } else {
            ProductEntity product = this.productService.findById(id);
            product.getFans().remove(currentUser);
            this.productService.addProductEntity(product);
            currentUser.getFavoriteProducts().remove(product);
            currentUser = this.userService.addInDb(currentUser);
            String redirectPage = "";
            if (previousPage.equals("promotion")) {
                redirectPage = "redirect:/products/all/promotion";
            } else if (previousPage.equals("favorites")) {
                redirectPage = String.format("redirect:/users/%d/favorites", currentUser.getId());
            } else if (previousPage.equals("info")) {
                redirectPage = String.format("redirect:/products/info/%d", id);
            } else if (previousPage.equals("all")) {
                redirectPage = "redirect:/products/all";
            }

            return redirectPage;
        }
    }

}
