package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.productViews.*;
import com.example.sellbuy.model.view.userViews.UserChatViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;

import java.util.List;
import java.util.Set;

public interface ProductService {

    void initializeProducts();

    void deleteFistProduct();

    ProductEntity addProductBindingModel(ProductAddBindingModel productAddBindingModel, SellAndBuyUserDetails sellAndBuyUser);

    List<ProductSearchViewModel> filterBy(ProductSearchingBindingModel productSearchingBindingModel, Long principalId,
                                          boolean getOnlyPromotions);

    ProductEntity findById(Long productId);

    ProductEntity addProductEntity(ProductEntity product);

    public boolean isConsist(Set<ProductEntity> productEntitySet, BaseProductViewModel product);

    public boolean isConsist(Set<ProductEntity> productEntitySet, ProductEntity product);

    void deleteProductById(Long id);

    Set<ProductEntity> findProductsByUserId(Long id);

    ProductEditViewModel findByIdProductSearchAndEditViewModel(Long id);

    ProductEntity updateProductById(Long id, ProductEditViewModel productEditViewModel);

    public ProductDetailsViewDto getAndIncreaseViewsProductById(Long id);

    Set<ProductChatViewModel> getProductsFromChatsByUserByUserId(Long id);

    Set<UserChatViewModel> findProductChattersByProductIdAndSellerId(Long productId, Long sellerId);

    List<ProductSearchViewModel> getTreeRandomProducts();

    public void changePromotions();

}
