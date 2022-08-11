package com.example.sellbuy.service.impl;

import com.example.sellbuy.event.InitializationEvent;
import com.example.sellbuy.model.entity.CategoryEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.repository.CategoryRepository;
import com.example.sellbuy.service.CategoryService;
import com.example.sellbuy.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @Order(3)
    @EventListener(InitializationEvent.class)
    @Override
    public void initializedCategories() {

        if (this.categoryRepository.count() == 0){
            for (CategoryEnum category : CategoryEnum.values()) {
                CategoryEntity categoryEntity =  new CategoryEntity();
                categoryEntity.
                        setCategory(category).
                        setDescription(category.name());
                this.categoryRepository.save(categoryEntity);
            }
        }

    }

    @Override
    public CategoryEntity findByCategory(CategoryEnum category) {
        return this.categoryRepository.findByCategory(category).get();
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }




}
