package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.CategoryEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.repository.CategoryRepository;
import com.example.sellbuy.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


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
