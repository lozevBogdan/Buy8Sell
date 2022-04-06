package com.example.sellbuy.model.entity;

import com.example.sellbuy.model.entity.enums.ConditionEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {


    @Enumerated(EnumType.STRING)
    @Column(name = "_condition", nullable = false)
    private ConditionEnum condition;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    private UserEntity seller;

    @OneToMany(mappedBy = "product")
    private Set<PictureEntity> pictures = new HashSet<>();

    @OneToMany(mappedBy = "productEntity")
    private Set<CommentEntity> comments= new HashSet<>();

    @ManyToMany(mappedBy = "products")
    private Set<CategoryEntity> categories= new HashSet<>();

    @ManyToMany
    private Set<UserEntity> fans= new HashSet<>();

    @Column
    private int views;

    @Column
    private boolean isPromo = false;

    public ProductEntity() {
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public ProductEntity setCondition(ConditionEnum condition) {
        this.condition = condition;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public ProductEntity setLocation(String location) {
        this.location = location;
        return this;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public ProductEntity setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public ProductEntity setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public ProductEntity setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public ProductEntity setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
        return this;
    }

    public Set<UserEntity> getFans() {
        return fans;
    }

    public ProductEntity setFans(Set<UserEntity> fans) {
        this.fans = fans;
        return this;
    }

    public int getViews() {
        return views;
    }

    public ProductEntity setViews(int views) {
        this.views = views;
        return this;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public ProductEntity setPromo(boolean promo) {
        isPromo = promo;
        return this;
    }
}
