package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.model.entity.enums.UserRoleEnum;
import com.example.sellbuy.model.view.ProductSearchViewModel;
import com.example.sellbuy.repository.UserRepository;
import com.example.sellbuy.security.CurrentUser;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final UserRoleServiceImpl userRoleService;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;


    public UserServiceImpl(UserRepository userRepository, UserRoleServiceImpl userRoleService, CurrentUser currentUser, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }


    private void initializeUsers() {

        if(userRepository.count() == 0) {

            UserEntity user1 = new UserEntity();
            user1.
                    setFirstName("Georgi").
                    setLastName("Georgiev").
                    setEmail("gosho@abv.bg").
                    setMobileNumber("0888888888").
                    setPassword("gosho");

            UserEntity user2 = new UserEntity();
            user2.
                    setFirstName("Petyr").
                    setLastName("Petrow").
                    setEmail("petyr@abv.bg").
                    setMobileNumber("0999999999").
                    setPassword(("petyr"));

            UserEntity user3 = new UserEntity();
            user3.
                    setFirstName("Ivan").
                    setLastName("Ivanov").
                    setEmail("ivan@abv.bg").
                    setMobileNumber("08933333333").
                    setPassword(("ivan"));

            UserRoleEntity adminRole = this.userRoleService.findByRole(UserRoleEnum.ADMIN);
            UserRoleEntity userRole = this.userRoleService.findByRole(UserRoleEnum.USER);

            user1.setRoles(Set.of(adminRole,userRole));
            user2.setRoles(Set.of(userRole));
            user3.setRoles(Set.of(userRole));

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        }


    }

    @Override
    public void initializeUsersAndRoles() {
        this.userRoleService.initializeRoles();
        initializeUsers();
    }

    @Override
    public UserEntity getByUsername(String username) {
        return userRepository.
                findByEmail(username).
                orElse(null);
    }

    @Override
    public void loginUser(UserLoginBindingModel userLoginBindingModel) {

        UserEntity userByEmailAndPassword = this.userRepository.findByEmailAndPassword(userLoginBindingModel.getEmail(),
                userLoginBindingModel.getPassword()).get();

        Long id = userByEmailAndPassword.getId();
        String email = userByEmailAndPassword.getEmail();


      currentUser.logInCurrUser(id,email);

    }

    @Override
    public void logoutCurrentUser() {
            this.currentUser.
                    setEmail(null).
                    setId(null);


    }

    @Override
    public boolean isEmailFree(String email) {
        return this.userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public boolean isExistUserWithEmailAndPassword(String email, String password) {
        return this.userRepository.
                findByEmailAndPassword(email,password).
                isEmpty();
    }

    @Override
    public void makeNewRegistration(UserRegisterBindingModel userRegisterBindingModel) {

        UserEntity newUser = this.modelMapper.map(userRegisterBindingModel,UserEntity.class);

        Set<UserRoleEntity> roles = new HashSet<>();

        if(this.userRepository.count() == 0){
            UserRoleEntity adminRoleEntity =
                    this.userRoleService.findByRole(UserRoleEnum.ADMIN);
            roles.add(adminRoleEntity);
        }

        UserRoleEntity useRoleEntity =
                this.userRoleService.findByRole(UserRoleEnum.USER);

        roles.add(useRoleEntity);

        newUser.setRoles(roles);


       newUser =  this.userRepository.save(newUser);

    }

    @Override
    public UserEntity getCurrentLoggedInUserEntity() {

        UserEntity currentLoggedInUser = null;

        Long id = currentUser.getId();

        if(id != null) {
            currentLoggedInUser = this.userRepository.findById(id).get();
        }
        System.out.println();
        return  currentLoggedInUser;
    }

    @Override
    public UserEntity addInDb(UserEntity userEntity) {
        this.userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public void addFavorProduct(ProductEntity product) {

        UserEntity byId = this.userRepository.findById(currentUser.getId()).get();
        byId.getFavoriteProducts().add(product);
        userRepository.save(byId);
    }

    @Override
    public Set<ProductEntity> getFavorListOf(Long id) {

        Set<ProductEntity> favoriteProducts =
                this.userRepository.findById(id).get().getFavoriteProducts();

        return favoriteProducts;
    }
}
