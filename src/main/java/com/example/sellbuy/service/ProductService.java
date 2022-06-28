package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;

public interface ProductService {

    void initializeProducts();

    void deleteFistProduct();

    ProductEntity addProductBindingModel(ProductAddBindingModel productAddBindingModel);

}
