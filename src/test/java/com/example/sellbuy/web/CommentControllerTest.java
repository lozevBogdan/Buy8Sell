package com.example.sellbuy.web;

import com.example.sellbuy.init.TestDataInit;
import com.example.sellbuy.model.binding.CommentBindingDto;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.service.CommentsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataInit testDataInit;

    @MockBean
    CommentsService commentsService;

   private UserEntity testUser;

   private ProductEntity testProduct;


    @BeforeEach
    void setUp(){

        testUser = this.testDataInit.createTestUser("test@abv.bg");
        testProduct = this.testDataInit.createTestProduct(
                "Test product 1,for testing purpose",
                "Test Product 1",
                BigDecimal.valueOf(20L),
                LocationEnum.SOFIA_GRAD,
                testUser,
                CategoryEnum.ELECTRONICS,
                true
        );
    }

    @AfterEach
    void tearDown(){
        this.testDataInit.cleanUpDatabase();
    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void post_addComment_Successfull() throws Exception {

        mockMvc.perform(post("/comments/add/" + testProduct.getId()).
                        param("textContent","Test content 1").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/products/info/" + testProduct.getId()));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void post_addComment_withoutTextContent_NotSuccessfull() throws Exception {


        mockMvc.perform(post("/comments/add/" + testProduct.getId()).
                        param("textContent","").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/products/info/" + testProduct.getId()));
    }

}
