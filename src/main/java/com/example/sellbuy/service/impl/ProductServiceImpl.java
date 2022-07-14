package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.ProductAddBindingModel;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.entity.enums.OrderBYEnum;
import com.example.sellbuy.model.view.productViews.BaseProductViewModel;
import com.example.sellbuy.model.view.productViews.ProductDetailsViewDto;
import com.example.sellbuy.model.view.productViews.ProductEditViewModel;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.repository.ProductRepository;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.service.*;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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


    public ProductServiceImpl(ProductRepository productRepository, PictureService pictureService,
                              UserService userService, CategoryService categoryService,
                              ModelMapper modelMapper, LocationService locationService,
                              CommentsService commentsService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.locationService = locationService;
        this.commentsService = commentsService;
    }

    @Transactional
    @Override
    public void initializeProducts() {

        if(productRepository.count() == 0) {
            UserEntity lozev = userService.getByEmail("lozev.bogdan@abv.bg");

            PictureEntity picture1 = pictureService.getFirstPicture();
            ProductEntity product1 = new ProductEntity();
            LocationEntity location =
                    this.locationService.findByLocation(LocationEnum.SOFIA_GRAD);

            CategoryEntity category = this.categoryService.findByCategory(CategoryEnum.HOUSEHOLD);

            product1.
                    setCondition(ConditionEnum.NEW).
                    setDescription("Perfect! German quality!").
                    setPrice(BigDecimal.valueOf(1500)).
                    setLocation(location).
                    setSeller(lozev).setPicture(picture1).
                    setTitle("shampoo").
                    setCategory(category).
                    setPicture(picture1);

            productRepository.save(product1);
        }



    }

    @Override
    public void deleteFistProduct() {
        productRepository.deleteAll();
    }

    @Override
    public ProductEntity addProductBindingModel(ProductAddBindingModel productAddBindingModel,
                                            @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser) {
        ProductEntity newProduct =
                this.modelMapper.map(productAddBindingModel,ProductEntity.class);

        CategoryEntity categoryEntity =
                this.categoryService.findByCategory(productAddBindingModel.getCategory());

        UserEntity seller =
                this.userService.getCurrentLoggedInUserEntityById(sellAndBuyUser.getId());

        LocationEntity location = this.locationService.
                findByLocation(productAddBindingModel.getLocation());

        newProduct.
                setSeller(seller).
                setCategory(categoryEntity).
                setLocation(location);

        //categoryEntity.getProducts().add(newProduct);

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setProduct(newProduct);

        if(productAddBindingModel.getUrlPicture() == null){
            pictureEntity.setUrl("https://main.admin.forth.gr/files/site/no-image.png");
        }else {
            pictureEntity.setUrl(productAddBindingModel.getUrlPicture());
        }

        newProduct.setPicture(pictureEntity);

        newProduct = this.productRepository.save(newProduct);

        //pictureEntity = this.pictureService.addPictureInDb(pictureEntity);

     //   this.categoryService.updateCategory(categoryEntity);

        return newProduct;
    }

    @Override

    public List<ProductSearchViewModel> filterBy(
            ProductSearchingBindingModel productSearchingBindingModel,Long idPrincipal){

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
                 this.modelMapper.map(product, ProductSearchViewModel.class);
         String pictureUrl;
         if(product.getPicture() == null){
                 pictureUrl="https://main.admin.forth.gr/files/site/no-image.png";
         }else {
             pictureUrl = product.getPicture().getUrl();
         }
             productSearchViewModel.setMainPicture(pictureUrl);
         //todo: cheking forn null @AuthenticationPrincipal!!!!!!!!
            UserEntity currentLoggedInUserEntity =
                    this.userService.getCurrentLoggedInUserEntityById(idPrincipal);

            // Check for favorites products for current user
         if(currentLoggedInUserEntity != null){
             Set<ProductEntity> favoriteProducts = currentLoggedInUserEntity.getFavoriteProducts();
             if(isConsist(favoriteProducts,product)){
                 productSearchViewModel.setProductIsFavorInCurrentUser(true);

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

    public boolean isConsist(Set<ProductEntity> productEntitySet, BaseProductViewModel product){
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
// todo: make this with use CascadeTypeAll an mapped by in other side
        ProductEntity productForDeleted =
                this.productRepository.
                        findById(id).get();

       // productForDeleted.setCategory(null);
            //todo: delete method doesnt work

        this.commentsService.deleteByProductId(id);
       // this.categoryService.deleteByProductId(id);
        this.userService.deleteByProductIdFrom(productForDeleted);
        this.productRepository.deleteById(id);
        this.pictureService.deleteByProductId(id);
    }

    @Override
    public Set<ProductEntity> findProductsByUserId(Long id) {
        return this.productRepository.findProductsBySellerId(id);
    }

    @Override
    public ProductEditViewModel findByIdProductSearchAndEditViewModel(Long id) {

        ProductEntity product = this.productRepository.findById(id).get();

        ProductEditViewModel editViewModel =
                this.modelMapper.map(product, ProductEditViewModel.class);

        //todo: change the picture way to get !!!
        PictureEntity pictureEntity = this.pictureService.findByProductId(id);

        editViewModel.setUrlPicture(pictureEntity.getUrl());

        return editViewModel;
    }

    @Override
    public ProductEntity updateProductById(Long id, ProductEditViewModel productEditViewModel) {
        ProductEntity product = this.productRepository.findById(id).get();
        return this.updateProduct(product,productEditViewModel);
    }

    private ProductEntity updateProduct(ProductEntity oldVersion,ProductEditViewModel newData ){

        CategoryEntity category = this.categoryService.findByCategory(newData.getCategory());
        LocationEntity location = this.locationService.findByLocation(newData.getLocation());

        oldVersion.
                setTitle(newData.getTitle()).
                setCondition(newData.getCondition()).
                setCategory(category).
                setDescription(newData.getDescription()).
                setPrice(newData.getPrice()).
                setLocation(location).
                setPromo(newData.getIsPromo());


        Optional<PictureEntity> picture =
                this.pictureService.findByUrl(newData.getUrlPicture());

        if(picture.isEmpty()){
            PictureEntity newPicture = new PictureEntity();
            newPicture.
                    setUrl(newData.getUrlPicture()).
                    setProduct(oldVersion);
            Long oldPictureId = oldVersion.getPicture().getId();
            oldVersion.setPicture(newPicture);
            //todo: here should delete a old picture from db
            this.pictureService.deleteOldPictureById(oldPictureId);

            newPicture = this.pictureService.addPictureInDb(newPicture);
        }

        oldVersion.setModified(LocalDateTime.now());
        return this.productRepository.save(oldVersion);
    }

    @Override
    public ProductDetailsViewDto getAndIncreaseViewsProductById(Long id){
        ProductEntity currentProduct = this.findById(id);
        currentProduct.setViews(currentProduct.getViews() + 1);
        currentProduct = this.addProductEntity(currentProduct);
        return this.modelMapper.map(currentProduct, ProductDetailsViewDto.class);
    }


}
