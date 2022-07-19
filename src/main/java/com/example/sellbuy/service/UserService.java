package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.view.userViews.UserEditViewModel;
import com.example.sellbuy.model.view.userViews.UserInfoViewModel;

import java.util.List;
import java.util.Set;

public interface UserService {

    void initializeUsersAndRoles();

    UserEntity getByEmail(String email);

    void loginUser(UserLoginBindingModel userLoginBindingModel);

    void logoutCurrentUser();

    boolean isEmailFree(String email);

    boolean isExistUserWithEmailAndPassword(String email,String password);

    void makeNewRegistration(UserRegisterBindingModel userRegisterBindingModel);

    UserEntity getCurrentLoggedInUserEntityById(Long sellAndBuyUser);

    UserEntity addInDb(UserEntity currentUser);

    void addFavorProduct(ProductEntity product);

    Set<ProductEntity> getFavorListOf(Long id);

    Set<ProductEntity> getMyProductsById(Long id);

    void deleteByProductIdFrom(ProductEntity id);

    UserEntity findById(Long authorId);

    UserEditViewModel findByIdUserEditViewModel(Long id);

    UserEntity updateUserByIdWithUserEditViewModel(Long userId,UserEditViewModel userEditViewModel);

    boolean isThisIsOldPasswordByUserId(String oldPassword, Long userId);

    UserEntity changePasswordByUserId(String newPassword, Long id);

    boolean isNewPasswordIsEqualToOldPassByUserId(String newPassword, Long id);

    List<UserInfoViewModel> getAllUsers();

    UserInfoViewModel getUserInfoViewModelByUserId(Long userId);
}
