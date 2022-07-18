package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.model.entity.enums.UserRoleEnum;
import com.example.sellbuy.model.view.userViews.UserEditViewModel;
import com.example.sellbuy.repository.UserRepository;
import com.example.sellbuy.securityUser.CurrentUser;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final UserRoleServiceImpl userRoleService;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    public UserServiceImpl(UserRepository userRepository, UserRoleServiceImpl userRoleService,
                           CurrentUser currentUser, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, UserDetailsService userDetailsService){
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }


    private void initializeUsers() {
        if(userRepository.count() == 0) {

            UserEntity user1 = new UserEntity();
            user1.
                    setFirstName("Bogdan").
                    setLastName("Lozev").
                    setEmail("lozev.bogdan@abv.bg").
                    setMobileNumber("0888888888").
                    setPassword(this.passwordEncoder.encode("123"));

            UserEntity user2 = new UserEntity();
            user2.
                    setFirstName("Petyr").
                    setLastName("Petrow").
                    setEmail("petyr@abv.bg").
                    setMobileNumber("0999999999").
                    setPassword(this.passwordEncoder.encode("123"));

            UserEntity user3 = new UserEntity();
            user3.
                    setFirstName("Ivan").
                    setLastName("Ivanov").
                    setEmail("ivan@abv.bg").
                    setMobileNumber("08933333333").
                    setPassword(this.passwordEncoder.encode("123"));

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
    public UserEntity getByEmail(String email) {
        return userRepository.
                findByEmail(email).
                orElse(null);
    }


// FROM MOBILELLE !!!!!!!!!!!!!!!!-----------------------------------------------------------
//    public void registerAndLogin(UserRegisterBindingModel userRegisterDTO) {
//        UserEntity newUser = modelMapper.map(userRegisterDTO,UserEntity.class);
//        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
//        this.userRepository.save(newUser);
//        login(newUser);
//    }
//
//    private void login(UserEntity userEntity) {
//        UserDetails userDetails =
//                userDetailsService.loadUserByUsername(userEntity.getEmail());
//        Authentication auth =
//                new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        userDetails.getPassword(),
//                        userDetails.getAuthorities()
//                );
//        SecurityContextHolder.
//                getContext().
//                setAuthentication(auth);
//    }


// because spring security
    @Override
    public void loginUser(UserLoginBindingModel userLoginBindingModel) {

//todo
//        UserEntity userByEmailAndPassword = this.userRepository.findByEmailAndPassword(userLoginBindingModel.getEmail(),
//                userLoginBindingModel.getPassword()).get();
//
//        Long id = userByEmailAndPassword.getId();
//        String email = userByEmailAndPassword.getEmail();
//
//
//      currentUser.logInCurrUser(id,email);
//
    }

    // because spring security
    @Override
    public void logoutCurrentUser() {
//            this.currentUser.
//                    setEmail(null).
//                    setId(null);
//
//
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
        newUser.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));

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
    public UserEntity getCurrentLoggedInUserEntityById(Long id) {
        return  this.userRepository.findById(id).get();
    }

    @Override
    public UserEntity addInDb(UserEntity userEntity) {
        return  this.userRepository.save(userEntity);
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

    @Override
    public Set<ProductEntity> getMyProductsById(Long id) {
        return this.userRepository.findById(id).get().getProducts();
    }

    @Override
    public void deleteByProductIdFrom(ProductEntity productForDelete) {
        UserEntity currentLoggedInUserEntity = this.getCurrentLoggedInUserEntityById(productForDelete.getSeller().getId());
        Set<ProductEntity> products = currentLoggedInUserEntity.getProducts();
        for (ProductEntity product : products) {
            if(product.getId() == productForDelete.getId()){
                products.remove(product);
                break;
            }
        }
        currentLoggedInUserEntity.setProducts(products);
        this.userRepository.save(currentLoggedInUserEntity);
    }

    @Override
    public UserEntity findById(Long authorId) {

        return this.userRepository.findById(authorId).orElse(null);
    }

    @Override
    public UserEditViewModel findByIdUserEditViewModel(Long id) {
        return this.modelMapper.map(this.findById(id),UserEditViewModel.class);
    }
}
