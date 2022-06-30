package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.view.ProductSearchViewModel;

import java.util.List;
import java.util.Set;

public interface UserService {

    void initializeUsersAndRoles();

    UserEntity getByUsername(String username);

    void loginUser(UserLoginBindingModel userLoginBindingModel);

    void logoutCurrentUser();

    boolean isEmailFree(String email);

    boolean isExistUserWithEmailAndPassword(String email,String password);

    void makeNewRegistration(UserRegisterBindingModel userRegisterBindingModel);

    UserEntity getCurrentLoggedInUserEntity();

    UserEntity addInDb(UserEntity currentUser);

    void addFavorProduct(ProductEntity product);

    Set<ProductEntity> getFavorListOf(Long id);

    Set<ProductEntity> getMyProductsById(Long id);
}
