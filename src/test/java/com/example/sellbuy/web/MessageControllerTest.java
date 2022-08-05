package com.example.sellbuy.web;


import com.example.sellbuy.init.TestDataInit;
import com.example.sellbuy.model.entity.PictureEntity;
import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.model.view.messages.MessageChatViewModel;
import com.example.sellbuy.model.view.productViews.ProductChatViewModel;
import com.example.sellbuy.model.view.userViews.UserChatViewModel;
import com.example.sellbuy.service.MessageService;
import com.example.sellbuy.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {

    private final Long userDetailsId = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataInit testDataInit;

    @MockBean
    ProductService productService;

    @MockBean
    MessageService messageService;

    @AfterEach
    void tearDown(){
        this.testDataInit.cleanUpDatabase();
    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void getChatsAboutAllProducts_withNullProductsChats_view() throws Exception {

        when(productService.getProductsFromChatsByUserByUserId(userDetailsId)).
                thenReturn(new HashSet<ProductChatViewModel>());

        mockMvc.perform(get("/messages/all")).
                andExpect(status().isOk()).
                andExpect(model().attribute("products", new HashSet<ProductChatViewModel>())).
                andExpect(view().name("messages-for-all-products"));


    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void getChatsAboutAllProducts_withOneProductChats_view() throws Exception {

        ProductChatViewModel productChatViewModel =
                new ProductChatViewModel().
                        setId(1L).
                        setTitle("Test").
                        setPicture(new PictureEntity().setUrl("test_picture"));

        when(productService.getProductsFromChatsByUserByUserId(userDetailsId)).
                thenReturn(Set.of(productChatViewModel));

        mockMvc.perform(get("/messages/all")).
                andExpect(status().isOk()).
                andExpect(model().attribute("products", Set.of(productChatViewModel))).
                andExpect(view().name("messages-for-all-products"));


    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void getChats_WithOneMessage_AboutOneProduct_view() throws Exception {

        UserEntity testUser = this.testDataInit.createTestUser("user@abv.bg");

        UserChatViewModel userChatViewModel = new UserChatViewModel().
                setId(testUser.getId()).
                setFullName(testUser.getFullName());

        ProductEntity testProduct = this.testDataInit.createTestProduct(
                "Test products for testing purpose",
                "Test product 1",
                BigDecimal.TEN,
                LocationEnum.SOFIA_GRAD,testUser, CategoryEnum.ELECTRONICS,false);

        when(productService.findProductChattersByProductIdAndSellerId(testProduct.getId(),userDetailsId)).
                thenReturn(Set.of(userChatViewModel));

        mockMvc.perform(get("/messages/products/" + testProduct.getId())).
                andExpect(status().isOk()).
                andExpect(model().attribute("senders", Set.of(userChatViewModel))).
                andExpect(model().attribute("productId", testProduct.getId())).
//                andExpect(model().attribute("currentUser", )).
                andExpect(view().name("messages-for-product"));


    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void getChats_WithoutMessages_AboutOneProduct_view() throws Exception {

        UserEntity testUser = this.testDataInit.createTestUser("user@abv.bg");

        ProductEntity testProduct = this.testDataInit.createTestProduct(
                "Test products for testing purpose",
                "Test product 1",
                BigDecimal.TEN,
                LocationEnum.SOFIA_GRAD,testUser, CategoryEnum.ELECTRONICS,false);

        when(productService.findProductChattersByProductIdAndSellerId(testProduct.getId(),userDetailsId)).
                thenReturn(new HashSet<>());

        when(productService.findById(testProduct.getId())).
                thenReturn(testProduct);

        mockMvc.perform(get("/messages/products/" + testProduct.getId())).
                andExpect(status().isOk()).
                andExpect(model().attribute("senders", new HashSet<>())).
                andExpect(model().attribute("productId", testProduct.getId())).
//                andExpect(model().attribute("currentUser", )).
                andExpect(model().attribute("seller", testProduct.getSeller().getId())).
                        andExpect(view().name("messages-for-product"));


    }



    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void get_ChatsMessages_FromSender_AboutProduct() throws Exception {

        UserEntity testUser = this.testDataInit.createTestUser("user@abv.bg");

        UserChatViewModel userChatViewModel = new UserChatViewModel().
                setId(testUser.getId()).
                setFullName(testUser.getFullName());

        ProductEntity testProduct = this.testDataInit.createTestProduct(
                "Test products for testing purpose",
                "Test product 1",
                BigDecimal.TEN,
                LocationEnum.SOFIA_GRAD,testUser, CategoryEnum.ELECTRONICS,false);

        MessageChatViewModel messageChatViewModel =
                new MessageChatViewModel().
                        setMessage("Test message").
                        setSender(userChatViewModel).
                        setCreated(LocalDateTime.now());

        when(messageService.findChatsMessagesByProductIdSenderIdReceiverId(
                testProduct.getId(),testUser.getId(),userDetailsId
        )).
                thenReturn(Set.of(messageChatViewModel));

        mockMvc.perform(get("/messages/send/" + testUser.getId() + "/" + testProduct.getId())).
                andExpect(status().isOk()).
                andExpect(model().attribute("allMessages", Set.of(messageChatViewModel))).
                andExpect(model().attribute("chatWitUserId",testUser.getId())).
                andExpect(view().name("messages-for-product"));


    }



}
