package com.example.sellbuy.web;


import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.productViews.ProductFavoriteViewModel;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserProductsControllerTest {

    private final Long userDetailsId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    ProductService productService;


    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    @Test
    void get_MyProductsPage_withoutAnyProducts() throws Exception {

        Set<ProductEntity> expectedMyProducts = new LinkedHashSet<>();

        List<ProductFavoriteViewModel> myProductsSearchViewModelList = new LinkedList<>();

        when(productService.findProductsByUserId(userDetailsId)).
                thenReturn(new LinkedHashSet<>());

        when(userService.returnFavors(expectedMyProducts,userDetailsId)).
                thenReturn(myProductsSearchViewModelList);

        mockMvc.perform(get("/users/products")).
                andExpect(status().isOk()).
                andExpect(model().attribute("myProductsSearchViewModelList", myProductsSearchViewModelList)).
                andExpect(view().name("my-products"));

    }


    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    @Test
    void get_return_favorites_products_view() throws Exception {

        mockMvc.perform(get("/users/favorites")).
                andExpect(status().isOk()).
                andExpect(view().name("products-favorites"));

    }



}
