package com.example.sellbuy.model.binding;

import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.ConditionEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductAddBindingModel {

    @NotNull
    @Size(min = 3,max = 20)
    private String title;

    @NotNull
    private ConditionEnum condition;

    @NotNull
    @Size(min = 5)
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Size(min = 3)
    private String location;


    private String urlPicture;

    @NotNull
    private CategoryEnum category;


    private boolean isPromo;


    public ProductAddBindingModel() {
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public ProductAddBindingModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductAddBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public ProductAddBindingModel setCondition(ConditionEnum condition) {
        this.condition = condition;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public ProductAddBindingModel setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public ProductAddBindingModel setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
        return this;
    }

    public boolean getIsPromo() {
        return isPromo;
    }

    public ProductAddBindingModel setPromo(boolean promo) {
        isPromo = promo;
        return this;
    }

    @Override
    public String toString() {
        return "ProductAddBindingModel{" +
                "title='" + title + '\'' +
                ", condition=" + condition +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", urlPicture='" + urlPicture + '\'' +
                ", category=" + category +
                ", isPromo=" + isPromo +
                '}';
    }
}
