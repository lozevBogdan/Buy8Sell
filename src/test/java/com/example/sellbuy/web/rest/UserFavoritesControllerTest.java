package com.example.sellbuy.web.rest;

import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.productViews.ProductFavoriteViewModel;
import com.example.sellbuy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserFavoritesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;


    @Test
    @WithMockUser(
            username = "test@example.com",
            roles = "USER"
    )
    public void getFavoritesProductsReturnedAsJsonANdStatusOk() throws Exception {

        Long userId = 1L;

        when(mockUserService.returnFavoritesViwModels(userId)).thenReturn(List.of(
                new ProductFavoriteViewModel().
                        setId(1L).
                        setPictureUrl("no-picture").
                        setTitle("Test Product 1").
                        setPrice(BigDecimal.TEN).
                        setPromo(true),
                        new ProductFavoriteViewModel().
                                setId(2L).
                                setPictureUrl("no-picture").
                                setTitle("Test Product 2").
                                setPrice(BigDecimal.ONE).
                                setPromo(false)
                ));

        mockMvc.perform(get("/api/users/" + userId + "/favorites")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].title",is("Test Product 1"))).
                andExpect(jsonPath("$.[0].promo",is(true))).
                andExpect(jsonPath("$.[1].title",is("Test Product 2"))).
                andExpect(jsonPath("$.[1].promo",is(false)));

    }


}
