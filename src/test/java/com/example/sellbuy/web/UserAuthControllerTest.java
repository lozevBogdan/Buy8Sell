package com.example.sellbuy.web;

import com.example.sellbuy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// for integration test use annotation @SpringBootTest - load a context
// @AutoConfigureMockMvc for using MockMVC
@SpringBootTest
@AutoConfigureMockMvc
public class UserAuthControllerTest {

    // with this we can send http request to server
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginShown() throws Exception {
        mockMvc.perform(get("/users/login")).
                andExpect(status().isOk()).
                andExpect(view().name("login"));
    }

    @Test
    void testRegistrationShown() throws Exception {
        mockMvc.perform(get("/users/register")).
                andExpect(status().isOk()).
                andExpect(view().name("register"));
    }


    @Test
    void testUserRegistration_Success() throws Exception {
       mockMvc.perform(post("/users/register").
               param("firstName","Ivan").
               param("lastName","Ivanov").
               param("email","test@abv.bg").
               param("mobileNumber","0888888888").
               param("password","12345").
               param("confirmPassword","12345").
               with(csrf())
       ).
               andExpect(status().is3xxRedirection()).
               andExpect(redirectedUrl("login"));
    }

    @Test
    void testUserRegistration_withDifferentPasswords_NotSuccessfull_redirectToRegisterPage() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("firstName","Ivan").
                        param("lastName","Ivanov").
                        param("email","test@abv.bg").
                        param("mobileNumber","0888888888").
                        param("password","12345").
                        param("confirmPassword","54321").
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/register"));
    }

    @Test
    void testUserLogin_withNotExistUser_NotSuccessfull_redirectToLoginPage() throws Exception {
        mockMvc.perform(post("/users/login").
                        param("email","non_exist_user@abv.bg").
                        param("password","12345").
                        with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(forwardedUrl("/users/login-error"));
    }


//    @Test
//    void testUserLogin_NotSuccessfull_redirectToLoginPage() throws Exception {
//        mockMvc.perform(post("/users/login-error").
//                        param("email","test@abv.bg")
//                ).
//                andExpect(status().is3xxRedirection()).
//                andExpect(redirectedUrl("/users/login"));
//    }

}

