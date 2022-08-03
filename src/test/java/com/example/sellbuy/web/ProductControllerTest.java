package com.example.sellbuy.web;

import com.example.sellbuy.model.entity.CategoryEntity;
import com.example.sellbuy.model.entity.LocationEntity;
import com.example.sellbuy.model.entity.PictureEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.view.productViews.ProductDetailsViewDto;
import com.example.sellbuy.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void loadAllProductsPage_with_notLoggedInUser() throws Exception {

        mockMvc.perform(get("/products/all")).
                andExpect(status().isOk()).
                andExpect(view().name("products-all-anonymous"));
    }

//    @Test
//    @WithMockUser(
//            username = "test@example.com",
//            roles = "USER"
//    )
//    void loadAllProductsPage_with_loggedInUser() throws Exception {
//
//        mockMvc.perform(get("/products/all")).
//                andExpect(status().isOk()).
//                andExpect(view().name("products-all"));
//    }


    @Test
    void loadAllPromotionsPage_with_notLoggedInUser() throws Exception {
        mockMvc.perform(get("/products/all/promotion")).
                andExpect(status().isOk()).
                andExpect(view().name("products-promotions-anonymous"));
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


}

