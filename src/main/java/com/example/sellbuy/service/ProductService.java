package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.ProductSearchViewModel;

import java.util.List;
import java.util.Set;

public interface ProductService {

    void initializeProducts();

    void deleteFistProduct();

    ProductEntity addProductBindingModel(ProductAddBindingModel productAddBindingModel);

    List<ProductSearchViewModel> filterBy(ProductSearchingBindingModel productSearchingBindingModel);

    ProductEntity findById(Long productId);

    void addProductEntity(ProductEntity product);
    public boolean isConsist(Set<ProductEntity> productEntitySet, ProductEntity product);
}
