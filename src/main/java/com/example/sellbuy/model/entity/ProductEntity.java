package com.example.sellbuy.model.entity;

import com.example.sellbuy.model.entity.enums.ConditionEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    private LocationEntity location;

    @ManyToOne
    private UserEntity seller;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "products_id_pictures_id",
            joinColumns = {
            @JoinColumn(name = "product_id",referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "picture_id",referencedColumnName = "id") })
    private PictureEntity picture;

    @OneToMany(mappedBy = "productEntity",fetch = FetchType.EAGER)
    private Set<CommentEntity> comments= new HashSet<>();

    @ManyToOne
    private CategoryEntity category;

    @ManyToMany(fetch = FetchType.EAGER)
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

    public String getTitle() {
        return title;
    }

    public ProductEntity setTitle(String title) {
        this.title = title;
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

    public UserEntity getSeller() {
        return seller;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public ProductEntity setLocation(LocationEntity location) {
        this.location = location;
        return this;
    }

    public ProductEntity setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public ProductEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public ProductEntity setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public ProductEntity setCategory(CategoryEntity category) {
        this.category = category;
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



    @Override
    public String toString() {
        return "ProductEntity{" +
                "condition=" + condition +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", views=" + views +
                ", isPromo=" + isPromo +
                '}';
    }


}
