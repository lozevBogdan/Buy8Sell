package com.example.sellbuy.model.view;

import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.ConditionEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductViewModel {

    private Long id;
    private ConditionEnum condition;
    private String description;
    private String title;
    private BigDecimal price;
    private LocationEntity location;
    private UserEntity seller;
    private CategoryEntity category ;
    private Set<PictureEntity> pictures = new HashSet<>();
    private Set<CommentEntity> comments= new HashSet<>();
    private Set<UserEntity> fans= new HashSet<>();
    private int views;
    private boolean isPromo;

    public Long getId() {
        return id;
    }

    public ProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public ProductViewModel setCondition(ConditionEnum condition) {
        this.condition = condition;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public ProductViewModel setLocation(LocationEntity location) {
        this.location = location;
        return this;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public ProductViewModel setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public ProductViewModel setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public ProductViewModel setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public ProductViewModel setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public Set<UserEntity> getFans() {
        return fans;
    }

    public ProductViewModel setFans(Set<UserEntity> fans) {
        this.fans = fans;
        return this;
    }

    public int getViews() {
        return views;
    }

    public ProductViewModel setViews(int views) {
        this.views = views;
        return this;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public ProductViewModel setPromo(boolean promo) {
        isPromo = promo;
        return this;
    }
}
