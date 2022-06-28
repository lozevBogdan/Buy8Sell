package com.example.sellbuy.model.binding;

public class ProductBindingModel {

    // todo: this shoul become a conditionEnum type
    private String condition;
    private String description;
    private int price;
    private String location;
    private String pictureUrl;

    public String getCondition() {
        return condition;
    }

    public ProductBindingModel setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public ProductBindingModel setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public ProductBindingModel setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public ProductBindingModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    @Override
    public String toString() {
        return "AddProductDto{" +
                "condition='" + condition + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
