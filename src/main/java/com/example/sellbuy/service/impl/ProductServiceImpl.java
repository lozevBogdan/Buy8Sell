package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.PictureEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import com.example.sellbuy.repository.ProductRepository;
import com.example.sellbuy.service.PictureService;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PictureService pictureService;
    private final UserService userService;

    public ProductServiceImpl(ProductRepository productRepository, PictureService pictureService, UserService userService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void initializeProductsAndPictures() {

        UserEntity ivan = userService.getByUsername("ivan");
        UserEntity petyr = userService.getByUsername("petyr");
        UserEntity gosho = userService.getByUsername("gosho");


        PictureEntity picture1 = new PictureEntity();

        picture1.setUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FMercedes-Benz_S-Class&psig=AOvVaw2ogX5Lfr43z3Tn7vlNpqdk&ust=1649355177808000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCPChhP-EgPcCFQAAAAAdAAAAABAD");


        ProductEntity product1 = new ProductEntity();
        product1.
                setCondition(ConditionEnum.NEW).
                setDescription("Perfect! German quality!").
                setPrice(BigDecimal.valueOf(1500)).
                setLocation("Sofiq, Bulgariq").
                setSeller(ivan);

        picture1.setProduct(product1);
        product1.setPictures((Set.of(picture1)));

        pictureService.putInDb(picture1);
        productRepository.save(product1);



    }
}
