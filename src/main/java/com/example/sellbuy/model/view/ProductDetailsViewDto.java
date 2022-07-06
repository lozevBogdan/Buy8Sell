package com.example.sellbuy.model.view;

import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProductDetailsViewDto {

    private ConditionEnum condition;

    private String description;

    private String title;

    private BigDecimal price;

    private LocationEntity location;

    private UserEntity seller;

    private Set<PictureEntity> pictures = new HashSet<>();

    private Set<CommentEntity> comments= new HashSet<>();

    private CategoryEntity category;

    private Set<UserEntity> fans= new HashSet<>();

    private int views;

    private boolean isPromo;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime modified;

    public ConditionEnum getCondition() {
        return condition;
    }

    public ProductDetailsViewDto setCondition(ConditionEnum condition) {
        this.condition = condition;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDetailsViewDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductDetailsViewDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDetailsViewDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public ProductDetailsViewDto setLocation(LocationEntity location) {
        this.location = location;
        return this;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public ProductDetailsViewDto setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public ProductDetailsViewDto setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public ProductDetailsViewDto setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public ProductDetailsViewDto setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public Set<UserEntity> getFans() {
        return fans;
    }

    public ProductDetailsViewDto setFans(Set<UserEntity> fans) {
        this.fans = fans;
        return this;
    }

    public int getViews() {
        return views;
    }

    public ProductDetailsViewDto setViews(int views) {
        this.views = views;
        return this;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public ProductDetailsViewDto setPromo(boolean promo) {
        isPromo = promo;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ProductDetailsViewDto setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public ProductDetailsViewDto setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }
}
