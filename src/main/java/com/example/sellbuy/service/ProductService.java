package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.ProductSearchViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;

import java.util.List;
import java.util.Set;

public interface ProductService {

    void initializeProducts();

    void deleteFistProduct();

    ProductEntity addProductBindingModel(ProductAddBindingModel productAddBindingModel, SellAndBuyUserDetails sellAndBuyUser);

    List<ProductSearchViewModel> filterBy(ProductSearchingBindingModel productSearchingBindingModel, Long id);

    ProductEntity findById(Long productId);

    ProductEntity addProductEntity(ProductEntity product);
    public boolean isConsist(Set<ProductEntity> productEntitySet, ProductEntity product);

    void deleteProductById(Long id);

    Set<ProductEntity> findProductsByUserId(Long id);
}
