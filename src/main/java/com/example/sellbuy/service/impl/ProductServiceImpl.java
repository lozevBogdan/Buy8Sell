package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.entity.enums.OrderBYEnum;
import com.example.sellbuy.model.view.ProductSearchViewModel;
import com.example.sellbuy.repository.ProductRepository;
import com.example.sellbuy.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PictureService pictureService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final LocationService locationService;

    public ProductServiceImpl(ProductRepository productRepository, PictureService pictureService, UserService userService, CategoryService categoryService, ModelMapper modelMapper, LocationService locationService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.locationService = locationService;
    }

    @Override
    @Transactional
    public void initializeProducts() {

        if(productRepository.count() == 0) {

            UserEntity ivan = userService.getByUsername("ivan");
            UserEntity petyr = userService.getByUsername("petyr");
            UserEntity gosho = userService.getByUsername("gosho");

            PictureEntity picture1 = pictureService.getFirstPicture();

            ProductEntity product1 = new ProductEntity();

            LocationEntity location = this.locationService.findByLocation(LocationEnum.SOFIA_GRAD);

            product1.
                    setCondition(ConditionEnum.NEW).
                    setDescription("Perfect! German quality!").
                    setPrice(BigDecimal.valueOf(1500)).
                    setLocation(location).
                    setSeller(ivan).
                    setPictures((Set.of(picture1))).
                    setTitle("shampoo");

            picture1.setProduct(product1);

            productRepository.save(product1);
        }



    }

    @Override
    public void deleteFistProduct() {
        productRepository.deleteAll();
    }

    @Override
    public ProductEntity addProductBindingModel(ProductAddBindingModel productAddBindingModel) {

        ProductEntity newProduct = this.modelMapper.map(productAddBindingModel,ProductEntity.class);

        CategoryEntity categoryEntity = this.categoryService.findByCategory(productAddBindingModel.getCategory());

        UserEntity seller = this.userService.getCurrentLoggedInUserEntity();

        newProduct.
                setSeller(seller).
                setCategory(categoryEntity);

        categoryEntity.getProducts().add(newProduct);

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setProduct(newProduct).setUrl(productAddBindingModel.getUrlPicture());

       // pictureEntity = this.pictureService.addPictureInDb(pictureEntity);

        newProduct.getPictures().add(pictureEntity);

      newProduct = this.productRepository.save(newProduct);

        this.categoryService.updateCategory(categoryEntity);

        System.out.println(newProduct);
        System.out.println(newProduct.getSeller().getEmail());


        return newProduct;
    }

    @Override
    public List<ProductSearchViewModel> filterBy(ProductSearchingBindingModel productSearchingBindingModel){

        String title = productSearchingBindingModel.getTitle();
        Double min = productSearchingBindingModel.getMin();
        Double max = productSearchingBindingModel.getMax();
        CategoryEnum category = productSearchingBindingModel.getCategory();
        LocationEnum location = productSearchingBindingModel.getLocation();
        OrderBYEnum orderBy = productSearchingBindingModel.getOrderBy();

        List<ProductEntity> allProducts = this.productRepository.findAll();
        List<ProductSearchViewModel> returnedList = new LinkedList<>();

        if(title != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getTitle().contains(title)).
                    collect(Collectors.toList());
        }

        if(min != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getPrice().doubleValue() >= min).
                    collect(Collectors.toList());
        }
        if(max != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getPrice().doubleValue() <= max).
                    collect(Collectors.toList());
        }
        if(category != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getCategory().getCategory().name().equals(category.name())).
                    collect(Collectors.toList());
        }
        if(location != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getLocation().getLocation().name().equals(location.name())).
                    collect(Collectors.toList());
        }
        if(orderBy != null){

            switch (orderBy){

                case VIEWS:
                    //todo: doesnt work correctly
                    allProducts.
                            sort((a,b)->b.getViews()-a.getViews());

                case NEWEST:
                    allProducts.
                            sort((a,b)->b.getCreated().compareTo(a.getCreated()));

                case LATEST:
                    allProducts.
                            sort((a,b)->a.getCreated().compareTo(b.getCreated()));

                case EXPENSIVEST:
                    allProducts.
                            sort((a,b)->a.getPrice().compareTo(b.getPrice()));

                case CHEAPEST:
                    allProducts.
                            sort((a,b)->b.getPrice().compareTo(a.getPrice()));

            }
        }

        UserEntity currentLoggedInUserEntity = this.userService.getCurrentLoggedInUserEntity();


        for (ProductEntity product : allProducts) {

         ProductSearchViewModel productSearchViewModel = this.modelMapper.map(product,ProductSearchViewModel.class);

         String pictureUrl;

         if(product.getPictures().size() == 0){
         pictureUrl="https://main.admin.forth.gr/files/site/no-image.png";

         }else {
             pictureUrl = product.getPictures().stream().findFirst().get().getUrl();
         }

           productSearchViewModel.setMainPicture(pictureUrl);

         // Check for favorites products for current user

         if(currentLoggedInUserEntity != null){
             if(currentLoggedInUserEntity.getFavoriteProducts().contains(product)){
                 productSearchViewModel.setProductIsFavorInCurrentUser(true);
             }
         }
           returnedList.add(productSearchViewModel);
        }

        return returnedList;
    }
}
