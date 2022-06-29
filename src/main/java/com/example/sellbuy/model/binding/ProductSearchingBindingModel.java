package com.example.sellbuy.model.binding;

import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.entity.enums.OrderBYEnum;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class ProductSearchingBindingModel {

    private String title;
    @Min(value = 0)
    private Double min;
    @Min(value = 0)
    private Double max;
    private CategoryEnum category;
    private LocationEnum location;
    private OrderBYEnum orderBy;

    public String getTitle() {
        return title;
    }

    public ProductSearchingBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public Double getMin() {
        return min;
    }

    public ProductSearchingBindingModel setMin(Double min) {
        this.min = min;
        return this;
    }

    public Double getMax() {
        return max;
    }

    public ProductSearchingBindingModel setMax(Double max) {
        this.max = max;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public ProductSearchingBindingModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public LocationEnum getLocation() {
        return location;
    }

    public ProductSearchingBindingModel setLocation(LocationEnum location) {
        this.location = location;
        return this;
    }

    public OrderBYEnum getOrderBy() {
        return orderBy;
    }

    public ProductSearchingBindingModel setOrderBy(OrderBYEnum orderBy) {
        this.orderBy = orderBy;
        return this;
    }
}
