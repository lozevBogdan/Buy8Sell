package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.CategoryEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;

public interface CategoryService {

    void initializedCategories();

    CategoryEntity findByCategory(CategoryEnum category);

    CategoryEntity updateCategory(CategoryEntity category);

}
