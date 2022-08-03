package com.example.sellbuy.web.rest;

import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.productViews.ProductFavoriteViewModel;
import com.example.sellbuy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserFavoritesController {

    private final UserService userService;

    public UserFavoritesController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/favorites")
    public ResponseEntity<List<ProductFavoriteViewModel>> getAllFavorites(@PathVariable Long id) {
        List<ProductFavoriteViewModel> returnFavoritesViwModels = this.userService.returnFavoritesViwModels(id);
        return ResponseEntity.ok(returnFavoritesViwModels);
    }

}
