package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.entity.enums.OrderBYEnum;
import com.example.sellbuy.model.view.ProductSearchViewModel;
import com.example.sellbuy.repository.ProductRepository;
import com.example.sellbuy.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PictureService pictureService;
    private final UserService userService;

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final LocationService locationService;
    private final CommentsService commentsService;


    public ProductServiceImpl(ProductRepository productRepository, PictureService pictureService, UserService userService, CategoryService categoryService, ModelMapper modelMapper, LocationService locationService, CommentsService commentsService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.locationService = locationService;
        this.commentsService = commentsService;
    }

    @Override
    public void initializeProducts() {

        if(productRepository.count() == 0) {
            UserEntity lozev = userService.getByEmail("lozev.bogdan@abv.bg");

            PictureEntity picture1 = pictureService.getFirstPicture();
            ProductEntity product1 = new ProductEntity();
            LocationEntity location =
                    this.locationService.findByLocation(LocationEnum.SOFIA_GRAD);

            CategoryEntity category = this.categoryService.findByCategory(CategoryEnum.HOME);

            product1.
                    setCondition(ConditionEnum.NEW).
                    setDescription("Perfect! German quality!").
                    setPrice(BigDecimal.valueOf(1500)).
                    setLocation(location).
                    setSeller(lozev).
                    setPictures((Set.of(picture1))).
                    setTitle("shampoo").
                    setCategory(category);
            picture1.setProduct(product1);

            productRepository.save(product1);
        }



    }

    @Override
    public void deleteFistProduct() {
        productRepository.deleteAll();
    }

    @Override
    public ProductEntity addProductBindingModel(ProductAddBindingModel
                                                            productAddBindingModel) {
        ProductEntity newProduct =
                this.modelMapper.map(productAddBindingModel,ProductEntity.class);

        CategoryEntity categoryEntity =
                this.categoryService.findByCategory(productAddBindingModel.getCategory());

        UserEntity seller =
                this.userService.getCurrentLoggedInUserEntity();

        LocationEntity location = this.locationService.findByLocation(productAddBindingModel.getLocation());

        newProduct.
                setSeller(seller).
                setCategory(categoryEntity).
                setLocation(location);

        //categoryEntity.getProducts().add(newProduct);

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.
                setProduct(newProduct).
                setUrl(productAddBindingModel.getUrlPicture());

        newProduct.getPictures().add(pictureEntity);

         newProduct = this.productRepository.save(newProduct);

        pictureEntity = this.pictureService.addPictureInDb(pictureEntity);

        this.categoryService.updateCategory(categoryEntity);

        return newProduct;
    }

    @Override
    public List<ProductSearchViewModel> filterBy(ProductSearchingBindingModel productSearchingBindingModel){

        String title = productSearchingBindingModel.getTitle();
        Double min = productSearchingBindingModel.getMin();
        Double max = productSearchingBindingModel.getMax();
        CategoryEnum category = productSearchingBindingModel.getCategory();
        LocationEnum location = productSearchingBindingModel.getLocation();
        OrderBYEnum orderBy = productSearchingBindingModel.getOrderBy();

        List<ProductEntity> allProducts = this.productRepository.findAll();
        List<ProductSearchViewModel> returnedList = new LinkedList<>();

        if(title != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getTitle().contains(title)).
                    collect(Collectors.toList());
        }

        if(min != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getPrice().doubleValue() >= min).
                    collect(Collectors.toList());
        }
        if(max != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getPrice().doubleValue() <= max).
                    collect(Collectors.toList());
        }
        if(category != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getCategory().getCategory().name().equals(category.name())).
                    collect(Collectors.toList());
        }
        if(location != null){
            allProducts = allProducts.
                    stream().
                    filter(p->p.getLocation().getLocation().name().equals(location.name())).
                    collect(Collectors.toList());
        }
        if(orderBy != null){
            //todo: doesnt work correctly sorted function
            switch (orderBy){

                case VIEWS:
                    //todo: doesnt work correctly
                    allProducts.
                            sort((a,b)->b.getViews()-a.getViews());

                case NEWEST:
                    allProducts.
                            sort((a,b)->b.getCreated().compareTo(a.getCreated()));

                case LATEST:
                    allProducts.
                            sort((a,b)->a.getCreated().compareTo(b.getCreated()));

                case EXPENSIVEST:
                    allProducts.
                            sort((a,b)->(a.getPrice().intValue())-(b.getPrice().intValue()));

                case CHEAPEST:
                    allProducts.
                            sort((a,b)->(b.getPrice().intValue())-(a.getPrice().intValue()));

            }
        }

        for (ProductEntity product : allProducts) {
         ProductSearchViewModel productSearchViewModel =
                 this.modelMapper.map(product,ProductSearchViewModel.class);
         String pictureUrl;
         if(product.getPictures().size() == 0){
                 pictureUrl="https://main.admin.forth.gr/files/site/no-image.png";
         }else {
             pictureUrl = product.getPictures().stream().findFirst().get().getUrl();
         }
             productSearchViewModel.setMainPicture(pictureUrl);
            UserEntity currentLoggedInUserEntity = this.userService.getCurrentLoggedInUserEntity();
            // Check for favorites products for current user
         if(currentLoggedInUserEntity != null){

             Set<ProductEntity> favoriteProducts = currentLoggedInUserEntity.getFavoriteProducts();

             if(isConsist(favoriteProducts,product)){
                 productSearchViewModel.setProductIsFavorInCurrentUser(true);
                 System.out.println();
             }
         }
           returnedList.add(productSearchViewModel);
        }


        return returnedList;
    }

    @Override
    public ProductEntity findById(Long productId) {
        return this.productRepository.findById(productId).get();
    }

    @Override
    public ProductEntity addProductEntity(ProductEntity product) {
       return this.productRepository.save(product);
    }

    public boolean isConsist(Set<ProductEntity> productEntitySet, ProductEntity product){

        for (ProductEntity productEntity : productEntitySet) {
            if(productEntity.getId() == product.getId()){
                return true;
            }
        }

        return false;
    }


    @Transactional
    @Override
    public void deleteProductById(Long id) {

        // with those activities, we avoid to use CascadeType between entities.

        ProductEntity productForDeleted =
                this.productRepository.
                        findById(id).get();

       // productForDeleted.setCategory(null);

        this.pictureService.deleteByProductId(id);
        this.commentsService.deleteByProductId(id);
        this.categoryService.deleteByProductId(id);
        this.userService.deleteByProductIdFrom(id);

        this.productRepository.deleteById(id);
    }

    @Override
    public Set<ProductEntity> findProductsByUserId(Long id) {
        return this.productRepository.findProductsBySellerId(id);
    }

}
