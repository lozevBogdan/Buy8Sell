package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;

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
}
