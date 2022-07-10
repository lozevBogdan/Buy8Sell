package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.CategoryEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.repository.CategoryRepository;
import com.example.sellbuy.service.CategoryService;
import com.example.sellbuy.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository,@Lazy ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
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

    @Override
    public void deleteByProductId(Long id) {

//        CategoryEnum category = this.productService.findById(id).getCategory().getCategory();
//
//        CategoryEntity categoryEntity = this.findByCategory(category);
//
//        Set<ProductEntity> products = categoryEntity.getProducts();
//
//        for (ProductEntity product : products) {
//            if(product.getId() == id){
//                products.remove(product);
//                break;
//            }
//        }
//        categoryEntity.setProducts(products);
//        this.updateCategory(categoryEntity);

    }


}
