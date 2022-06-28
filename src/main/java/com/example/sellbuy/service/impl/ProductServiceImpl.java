package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.entity.CategoryEntity;
import com.example.sellbuy.model.entity.PictureEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import com.example.sellbuy.repository.ProductRepository;
import com.example.sellbuy.service.CategoryService;
import com.example.sellbuy.service.PictureService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PictureService pictureService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, PictureService pictureService, UserService userService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
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
            product1.
                    setCondition(ConditionEnum.NEW).
                    setDescription("Perfect! German quality!").
                    setPrice(BigDecimal.valueOf(1500)).
                    setLocation("Sofiq, Bulgariq").
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

      newProduct = this.productRepository.save(newProduct);

        this.categoryService.updateCategory(categoryEntity);

        System.out.println(newProduct);
        System.out.println(newProduct.getSeller().getEmail());


        return newProduct;
    }
}
