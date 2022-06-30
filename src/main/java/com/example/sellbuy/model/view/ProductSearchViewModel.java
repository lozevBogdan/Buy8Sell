package com.example.sellbuy.model.view;

import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.ConditionEnum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductSearchViewModel {

    private Long id;
    private ConditionEnum condition;
    private String description;
    private String title;
    private BigDecimal price;
    private LocationEntity location;
    private UserEntity seller;
    private CategoryEntity category ;
    private String mainPicture;
    private boolean isPromo;
    private boolean isProductIsFavorInCurrentUser = false;

    public boolean isProductIsFavorInCurrentUser() {
        return isProductIsFavorInCurrentUser;
    }

    public ProductSearchViewModel setProductIsFavorInCurrentUser(boolean productIsFavorInCurrentUser) {
        isProductIsFavorInCurrentUser = productIsFavorInCurrentUser;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ProductSearchViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public ProductSearchViewModel setCondition(ConditionEnum condition) {
        this.condition = condition;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductSearchViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductSearchViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductSearchViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public ProductSearchViewModel setLocation(LocationEntity location) {
        this.location = location;
        return this;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public ProductSearchViewModel setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public ProductSearchViewModel setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public ProductSearchViewModel setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
        return this;
    }


    public boolean isPromo() {
        return isPromo;
    }

    public ProductSearchViewModel setPromo(boolean promo) {
        isPromo = promo;
        return this;
    }
}
