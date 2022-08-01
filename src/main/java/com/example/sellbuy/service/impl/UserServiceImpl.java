package com.example.sellbuy.service.impl;

import com.example.sellbuy.event.InitializationEvent;
import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.model.entity.enums.UserRoleEnum;
import com.example.sellbuy.model.view.productViews.ProductFavoriteViewModel;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.model.view.userViews.UserEditViewModel;
import com.example.sellbuy.model.view.userViews.UserInfoViewModel;
import com.example.sellbuy.repository.UserRepository;
import com.example.sellbuy.securityUser.CurrentUser;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final UserRoleServiceImpl userRoleService;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final ProductService productService;


    public UserServiceImpl(UserRepository userRepository, UserRoleServiceImpl userRoleService,
                           CurrentUser currentUser, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
                           @Lazy ProductService productService){
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.productService = productService;
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
    @Order(2)
    @EventListener(InitializationEvent.class)
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

    @Override
    public UserEntity updateUserByIdWithUserEditViewModel(Long userId, UserEditViewModel userEditViewModel) {
        UserEntity userEntity = this.userRepository.findById(userId).get();
            return this.userRepository.
                       save(this.updateUserByUserEditViewModel(userEntity,userEditViewModel));
    }

    @Override
    public boolean isThisIsOldPasswordByUserId(String oldPassword, Long userId) {
       String currentUserPassword = this.userRepository.findById(userId).get().getPassword();
        return passwordEncoder.matches(oldPassword,currentUserPassword);
    }

    @Override
    public UserEntity changePasswordByUserId(String newPassword, Long id) {
        UserEntity user = this.userRepository.findById(id).get();
        user.setPassword(this.passwordEncoder.encode(newPassword));
        user.setModified(LocalDateTime.now());
        return this.userRepository.save(user);
    }

    @Override
    public boolean isNewPasswordIsEqualToOldPassByUserId(String newPassword, Long id) {
        return this.passwordEncoder.matches(newPassword,this.userRepository.findById(id).get().getPassword());
    }

    @Override
    public List<UserInfoViewModel> getAllUsers() {
        return this.userRepository.
                findAll().
                stream().
                map(this::mapToUserInfoViewModel).
                collect(Collectors.toList());
    }

    @Override
    public UserInfoViewModel getUserInfoViewModelByUserId(Long userId) {
        UserEntity userEntity = this.userRepository.findById(userId).orElse(null);
        if(userEntity == null){
            return null;
        }
        UserInfoViewModel userViewModel =
                this.modelMapper.map(userEntity,UserInfoViewModel.class);
       userViewModel.setAdmin(userViewModel.isHaveAdminRole());

        return userViewModel;
    }

    @Override
    public UserEntity updateUserByIdWithUserInfoViewModelAndIsAmin(Long userId,
                                         UserInfoViewModel userInfoViewModel,boolean isAdmin) {
        UserRoleEntity userRole = this.userRoleService.findByRole(UserRoleEnum.USER);
        UserRoleEntity adminRole = this.userRoleService.findByRole(UserRoleEnum.ADMIN);

        Set<UserRoleEntity> newRoles = new HashSet<>();
        newRoles.add(userRole);

        if(isAdmin || userId == 1L){
            newRoles.add(adminRole);
        }

        UserEntity userEntity = this.userRepository.findById(userId).get();
        userEntity.setRoles(newRoles).
                setFirstName(userInfoViewModel.getFirstName()).
                setLastName(userInfoViewModel.getLastName()).
                setEmail(userInfoViewModel.getEmail()).
                setMobileNumber(userInfoViewModel.getMobileNumber());
        userEntity.setModified(LocalDateTime.now());
        return this.userRepository.save(userEntity);
    }

    private UserInfoViewModel mapToUserInfoViewModel(UserEntity userEntity){
        return this.modelMapper.map(userEntity,UserInfoViewModel.class);
    }

    private UserEntity updateUserByUserEditViewModel(UserEntity userEntity,UserEditViewModel userEditViewModel){
        userEntity.setModified(LocalDateTime.now());
        userEntity.
                setFirstName(userEditViewModel.getFirstName()).
                setLastName(userEditViewModel.getLastName()).
                setEmail(userEditViewModel.getEmail()).
                setMobileNumber(userEditViewModel.getMobileNumber());
        return userEntity;
    }

    @Override
    public List<ProductFavoriteViewModel> returnFavors(Set<ProductEntity> favorProducts, Long userId){

        List<ProductFavoriteViewModel> returnedList = new LinkedList<>();

        for (ProductEntity product : favorProducts) {

//            ProductSearchViewModel productSearchViewModel =
//                    this.modelMapper.map(product, ProductSearchViewModel.class);

            ProductFavoriteViewModel productFavoriteViewModel =
                    this.modelMapper.map(product, ProductFavoriteViewModel.class);

            System.out.println();

//            productSearchViewModel.setMainPicture(product.getPicture().getUrl());

//            UserEntity currentLoggedInUserEntity =
//                    this.getCurrentLoggedInUserEntityById(userId);

            // Check for favorites products for current user
//            if(currentLoggedInUserEntity != null){
//                Set<ProductEntity> favoriteProducts =
//                        currentLoggedInUserEntity.getFavoriteProducts();
//
//                if(this.productService.isConsist(favoriteProducts,product)){
//                    productSearchViewModel.setProductIsFavorInCurrentUser(true);
//                }
//            }
            returnedList.add(productFavoriteViewModel);
        }
        return returnedList;
    }

    @Override
    public boolean checkByIdIsAdmin(Long userId) {

        Set<UserEntity> admins = this.userRepository.
                findUserEntitiesByRolesContaining(this.userRoleService.findByRole(UserRoleEnum.ADMIN));

        for (UserEntity admin : admins) {
            if (admin.getId().equals(userId)){
                return true;
            }
        }
        return false;
    }
}
