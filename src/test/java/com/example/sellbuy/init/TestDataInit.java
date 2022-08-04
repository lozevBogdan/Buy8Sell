package com.example.sellbuy.init;

import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.entity.enums.UserRoleEnum;
import com.example.sellbuy.repository.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TestDataInit {

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final UserRoleRepository userRoleRepository;
    private final LocationRepository locationRepository;




    public TestDataInit(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, UserRoleRepository userRoleRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRoleRepository = userRoleRepository;
        this.locationRepository = locationRepository;
    }

    private void initializeRoles() {
        if (userRoleRepository.count() == 0) {

            UserRoleEntity adminRole = new UserRoleEntity().setRole(UserRoleEnum.ADMIN);
            UserRoleEntity userRole = new UserRoleEntity().setRole(UserRoleEnum.USER);

            userRoleRepository.saveAll(List.of(adminRole,userRole));
        }
    }

    private void initializeLocations() {
        if(this.locationRepository.count() == 0){
            for (LocationEnum locationEnum : LocationEnum.values()) {
                LocationEntity newLocation = new LocationEntity();
                newLocation.setLocation(locationEnum);
                this.locationRepository.save(newLocation);
            }
        }
    }


    private void initializeCategories(){

        if (this.categoryRepository.count() == 0){
            for (CategoryEnum category : CategoryEnum.values()) {
                CategoryEntity categoryEntity =  new CategoryEntity();
                categoryEntity.
                        setCategory(category).
                        setDescription(category.name());
                this.categoryRepository.save(categoryEntity);
            }
        }
    }

    public UserEntity createTestAdmin(String email) {

        this.initializeRoles();

        UserEntity admin = new UserEntity().
                setEmail(email).
                setFirstName("Admin").
                setLastName("Adminov").
                setMobileNumber("0888888888").
                setPassword("12345").
                setRoles(Set.of(
                        this.userRoleRepository.findByRole(UserRoleEnum.USER),
                        this.userRoleRepository.findByRole(UserRoleEnum.ADMIN)
                ));

        return userRepository.save(admin);
    }

    public UserEntity createTestUser(String email) {

        this.initializeRoles();

        UserEntity user = new UserEntity().
                setEmail(email).
                setFirstName("User").
                setLastName("Userov").
                setMobileNumber("0888888888").
                setPassword("12345").
                setRoles(userRoleRepository.
                        findAll().stream().
                        filter(r -> r.getRole() != UserRoleEnum.ADMIN).
                        collect(Collectors.toSet()));

        return userRepository.save(user);
    }


    public ProductEntity createTestProduct( String description, String title,
                                                  BigDecimal price, LocationEnum location, UserEntity seller,
                                                  CategoryEnum category, boolean isPromo){

        initializeLocations();
        initializeCategories();

        LocationEntity locationEntity = this.locationRepository.findByLocation(location).get();
        CategoryEntity categoryEntity = this.categoryRepository.findByCategory(category).get();

        ProductEntity product = new ProductEntity();
        product.
                setDescription(description).
                setTitle(title).
                setPrice(price).
                setLocation(locationEntity).
                setSeller(seller).setCategory(categoryEntity).setPromo(isPromo);

        return this.productRepository.save(product);


    }

    public List<ProductEntity> getAllProducts(){
      return   this.productRepository.findAll();
    }

    public void cleanUpDatabase() {
        productRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        categoryRepository.deleteAll();
        locationRepository.deleteAll();
    }



}
