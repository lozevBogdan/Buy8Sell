package com.example.sellbuy.web;

import com.example.sellbuy.init.TestDataInit;
import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.view.productViews.ProductDetailsViewDto;
import com.example.sellbuy.model.view.productViews.ProductEditViewModel;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.service.ProductService;


import com.example.sellbuy.util.TestUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.rules.TestName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private final Long userDetailsId = 1L;

    @MockBean
    ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private UserEntity testUser;

    private UserEntity testAdmin;

    @Autowired
    private TestDataInit testDataInit;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        testUser = this.testDataInit.createTestUser("user@abv.bg");
        testAdmin = this.testDataInit.createTestAdmin("admin@gmail.bg");}
    @AfterEach
    void tearDown(){
        testDataInit.cleanUpDatabase();
    }

//    @Inject
//    private EntityManager em;
//
//    @Inject
//    PlatformTransactionManager txManager;


    //Unfortunately, we can't do easily @WithUserDetails with @Before,
    // because Spring @WithUserDetails annotation will invoke Spring security context test listener,
    // before running setUp method with @Before.
    // In this reason we will use @BeforeTransaction, @AfterTransaction,
    // annotated test method with @Transactional and use @Inject to inject
    // PlatformTransactionManager txManager and  private EntityManager em;
//    @BeforeTransaction
//    public void setup() {
//        new TransactionTemplate(txManager).execute(status -> {
//            testUser = this.testDataInit.createTestUser("test@abv.bg");
//            return null;
//        });
//    }
//
//    @AfterTransaction
//    public void cleanup() {
//        new TransactionTemplate(txManager).execute(status -> {
//            // Check if the entity is managed by EntityManager.
//            // If not, make it managed with merge() and remove it.
//            em.remove(em.contains(testUser) ? testUser : em.merge(testUser));
//            return null;
//        });
//    }


    @Test
    void loadAllProductsPage_with_notLoggedInUser() throws Exception {
        mockMvc.perform(get("/products/all")).
                andExpect(status().isOk()).
                andExpect(view().name("products-all-anonymous"));
    }

    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    @Test
    void loadAllProductsPage_with_loggedInUser() throws Exception {

        mockMvc.perform(get("/products/all")).
                andExpect(status().isOk()).
                andExpect(view().name("products-all"));
    }


    @Test
    void loadAllPromotionsPage_with_notLoggedInUser() throws Exception {
        mockMvc.perform(get("/products/all/promotion")).
                andExpect(status().isOk()).
                andExpect(view().name("products-promotions-anonymous"));
    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void loadAllPromotionsPage_with_loggedInUser() throws Exception {

        mockMvc.perform(get("/products/all/promotion")).
                andExpect(status().isOk()).
                andExpect(view().name("products-promotions"));
    }


    @Test
    void getInfoForProduct_with_notLoggedInUser_Successfull() throws Exception {

        Long productId = 1L;

        UserEntity seller = new UserEntity();
        seller.setMobileNumber("0893250782");

        PictureEntity picture = new PictureEntity();
        picture.setUrl("no-url").setId(1L);

        ProductDetailsViewDto productDetailsViewDto =(ProductDetailsViewDto) new ProductDetailsViewDto().
                setPicture(picture).
                setViews(1).
                setComments(new HashSet<>()).setLocation(new LocationEntity().setLocation(LocationEnum.SOFIA_GRAD)).
                setCategory(new CategoryEntity().setCategory(CategoryEnum.ELECTRONICS)).
                setSeller(seller);

        when(productService.getAndIncreaseViewsProductById(productId)).
                thenReturn(productDetailsViewDto);

        mockMvc.perform(get("/products/info/"+productId)).
                andExpect(status().isOk()).
                andExpect(view().name("product-Info-anonymous"));
    }

    @Test
    void getInfoForProduct_with_notLoggedInUser_NotSuccessfull_ThrowObjectNotFoundException() throws Exception {
        Long productId = 1L;
        mockMvc.perform(get("/products/info/"+productId)).
                andExpect(status().isNotFound()).
                andExpect(view().name("object-not-found"));
    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void addProduct_with_loggedInUser_Successfull() throws Exception {

        mockMvc.perform(post("/products/add").
                        param("title","Test Product").
                        param("category",CategoryEnum.ELECTRONICS.name()).
                        param("description","Testov product").
                        param("price","100").
                        param("location",LocationEnum.SOFIA_GRAD.name()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/products"));
    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void deleteProduct_correctRedirectAfterDeleteProduct() throws Exception {

                ProductEntity testProduct1 = this.testDataInit.createTestProduct(
                "Test product 1,for testing purpose",
                "Test Product 1",
                BigDecimal.valueOf(20L),
                LocationEnum.SOFIA_GRAD,
                this.testUser,
                CategoryEnum.ELECTRONICS,
                true
        );

        mockMvc.perform(delete("/products/delete/" + testProduct1.getId()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/products"));
    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void addProduct_WithoutTitle_with_loggedInUser_NotSuccessfull_RedirectToAddProductPage() throws Exception {

        mockMvc.perform(post("/products/add").
                        param("category",CategoryEnum.ELECTRONICS.name()).
                        param("description","Testov product").
                        param("price","100").
                        param("location",LocationEnum.SOFIA_GRAD.name()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/products/add"));
    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void addProduct_loadingView() throws Exception {

        mockMvc.perform(get("/products/add")).
                andExpect(status().isOk()).
                andExpect(view().name("product-add"));
    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void post_allProductPage_with_loggedInUser_Successfull() throws Exception {

//todo:check this test
//
//        ProductEntity testProduct1 = this.testDataInit.createTestProduct(
//                "Test product 1,for testing purpose",
//                "Test Product 1",
//                BigDecimal.valueOf(20L),
//                LocationEnum.SOFIA_GRAD,
//                this.testUser,
//                CategoryEnum.ELECTRONICS,
//                true
//        );
//
//        ProductEntity testProduct2 = this.testDataInit.createTestProduct(
//                "Test product 2,for testing purpose",
//                "Test Product 2",
//                BigDecimal.valueOf(10L),
//                LocationEnum.SOFIA_GRAD,
//                this.testUser,
//                CategoryEnum.ELECTRONICS,
//                false
//        );
//
//        List<ProductSearchViewModel> testProducts =
//                Stream.of(testProduct1, testProduct2).
//                        map(p->(this.modelMapper.map(p,ProductSearchViewModel.class))).
//                        collect(Collectors.toList());
//
//        ProductSearchingBindingModel productSearchingBindingModel = new ProductSearchingBindingModel().
//                setTitle("Test Product").
//                setLocation(null).
//                setCategory(null).
//                setOrderBy(null).
//                setMax(null).
//                setMin(null);
//
//
//        when(productService.filterBy(
//                productSearchingBindingModel,userDetailsId,false)
//        ).
//                thenReturn(testProducts);


        mockMvc.perform(post("/products/all").
                        param("title","Test Product").
                        with(csrf())
                ).
                andExpect(status().isOk()).
 //               andExpect(model().attribute("productSearchViewModelList", testProducts)).
                andExpect((view().name("products-all")));
    }




    @Test
    void allProductPage_view_with_NotLoggedInUser_Successfull() throws Exception {

//        ProductEntity testProduct1 = this.testDataInit.createTestProduct(
//                "Test product 1,for testing purpose",
//                "Test Product 1",
//                BigDecimal.ONE,
//                LocationEnum.SOFIA_GRAD,
//                this.testUser,
//                CategoryEnum.ELECTRONICS,
//                true
//        );
//
//        ProductEntity testProduct2 = this.testDataInit.createTestProduct(
//                "Test product 2,for testing purpose",
//                "Test Product 2",
//                BigDecimal.TEN,
//                LocationEnum.SOFIA_GRAD,
//                this.testUser,
//                CategoryEnum.ELECTRONICS,
//                false
//        );

        mockMvc.perform(post("/products/all").
                        param("title","Test Product").
                        with(csrf())
                ).
                andExpect(status().isOk()).
              //  andExpect(model().attribute("productSearchViewModelList", List.of(testProduct1,testProduct2))).
                andExpect((view().name("products-all-anonymous")));
    }

    @Test
    void allPromotionsProductPage_view_with_NotLoggedInUser_Successfull() throws Exception {

        mockMvc.perform(post("/products/all/promotion").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                        andExpect((view().name("products-promotions-anonymous")));
    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void allPromotionsProductPage_view_with_LoggedInUser_Successfull() throws Exception {

        mockMvc.perform(post("/products/all/promotion").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect((view().name("products-promotions")));
    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void searchingInAllProductPage_withMinPriceBiggerThenMaxPrice_with_loggedInUser_Redirect() throws Exception {

        mockMvc.perform(post("/products/all").
                        param("min","50").
                        param("max","10").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/products/all"));
    }


    @Test
    void searchingInAllProductPage_withMinPriceBiggerThenMaxPrice_with_NotLoggedInUser_Redirect() throws Exception {

        mockMvc.perform(post("/products/all").
                        param("min","50").
                        param("max","10").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/products/all"));
    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void loadEditProductPage_with_loggedInUser() throws Exception {

        when(productService.
                isCurrentUserHaveAuthorizationToEditProductCheckingBySellerIdAndCurrentUserId(
                        1L, userDetailsId
                )).thenReturn(true);

        when(productService.findByIdProductSearchAndEditViewModel(1L)).thenReturn(

                new ProductEditViewModel().
                        setTitle("Test edit").
                        setCategory(CategoryEnum.ELECTRONICS).
                        setDescription("This is for testing purpose.").
                        setLocation(LocationEnum.SOFIA_GRAD).
                        setPrice(BigDecimal.TEN).setUrlPicture("test_picture_url")
        );

        mockMvc.perform(get("/products/edit/"+1L)).
                andExpect(status().isOk()).
                andExpect(view().name("product-edit"));
    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void loadEditProductPage_with_loggedInUser_with_NoAuthorization() throws Exception {

        when(productService.
                isCurrentUserHaveAuthorizationToEditProductCheckingBySellerIdAndCurrentUserId(
                        1L,this.testUser.getId()
                )).thenReturn(false);

        mockMvc.perform(get("/products/edit/"+1L)).
                andExpect(status().isUnauthorized()).
                andExpect(view().name("not-authorized"));
    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postEditProductPage_with_loggedInUser_with_NoAuthorization() throws Exception {

        when(productService.
                isCurrentUserHaveAuthorizationToEditProductCheckingBySellerIdAndCurrentUserId(
                        1L,this.testUser.getId()
                )).thenReturn(false);

        mockMvc.perform(post("/products/edit/"+1L)
                        .with(csrf())).
                andExpect(status().isUnauthorized()).
                andExpect(view().name("not-authorized"));
    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postEditProductPage_with_loggedInUser_with_EmptyParams_NoSuccess() throws Exception {

        Long productId = 1L;

        when(productService.
                isCurrentUserHaveAuthorizationToEditProductCheckingBySellerIdAndCurrentUserId(
                        productId,userDetailsId
                )).thenReturn(true);

        mockMvc.perform(post("/products/edit/"+productId)
                        .with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/products/edit/" + productId ));
    }




}

