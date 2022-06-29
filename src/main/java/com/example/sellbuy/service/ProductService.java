package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.ProductSearchViewModel;

import java.util.List;

public interface ProductService {

    void initializeProducts();

    void deleteFistProduct();

    ProductEntity addProductBindingModel(ProductAddBindingModel productAddBindingModel);

    List<ProductSearchViewModel> filterBy(ProductSearchingBindingModel productSearchingBindingModel);
}
