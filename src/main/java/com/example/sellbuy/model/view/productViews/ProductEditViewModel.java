package com.example.sellbuy.model.view.productViews;

import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductEditViewModel {

    private Long id;

    @NotNull
    @Size(min = 3,max = 20)
    private String title;

    private ConditionEnum condition;

    @NotNull
    @Size(min = 5)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private LocationEnum location;


    private String urlPicture;

    @NotNull
    private CategoryEnum category;

    private boolean isPromo;

    public String getTitle() {
        return title;
    }

    public ProductEditViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ProductEditViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public ProductEditViewModel setCondition(ConditionEnum condition) {
        this.condition = condition;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEditViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEditViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public ProductEditViewModel setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public ProductEditViewModel setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public ProductEditViewModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public boolean getIsPromo() {
        return isPromo;
    }

    public ProductEditViewModel setPromo(boolean promo) {
        isPromo = promo;
        return this;
    }
}
