package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.PasswordChangingBindingModel;
import com.example.sellbuy.model.view.userViews.UserEditViewModel;
import com.example.sellbuy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserProfileControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    UserService userService;

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void loadingMyProfile_view_Successfull() throws Exception {

        Long userDetailsId = 1L;

        UserEditViewModel userEditViewModel = new UserEditViewModel().
                setId(userDetailsId).
                setEmail("test@abv.bg").
                setMobileNumber("0888888888").
                setFirstName("Test").
                setLastName("Testov");

        when(userService.findByIdUserEditViewModel(userDetailsId)).
                thenReturn(userEditViewModel);

        mockMvc.perform(get("/users/profile")).
                andExpect(status().isOk()).
                andExpect(model().attribute("userEditViewModel", userEditViewModel)).
                andExpect(view().name("my-profile"));

    }

    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void loadingMyProfile_EditPage_view_Successfull() throws Exception {

        Long userDetailsId = 1L;

        UserEditViewModel userEditViewModel = new UserEditViewModel().
                setId(userDetailsId).
                setEmail("test@abv.bg").
                setMobileNumber("0888888888").
                setFirstName("Test").
                setLastName("Testov");

        when(userService.findByIdUserEditViewModel(userDetailsId)).
                thenReturn(userEditViewModel);

        mockMvc.perform(get("/users/profile/edit")).
                andExpect(status().isOk()).
                andExpect(model().attribute("userEditViewModel", userEditViewModel)).
                andExpect(view().name("my-profile-edit"));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postMyProfile_SaveInfo_withoutChanges_Successfull() throws Exception {

        Long userDetailsId = 1L;

        UserEditViewModel userEditViewModel = new UserEditViewModel().
                setId(userDetailsId).
                setEmail("test@abv.bg").
                setMobileNumber("0888888888").
                setFirstName("Test").
                setLastName("Testov");

        mockMvc.perform(post("/users/profile/save").
                        with(csrf()).
                        param("firstName",userEditViewModel.getFirstName()).
                        param("lastName",userEditViewModel.getLastName()).
                        param("email", userEditViewModel.getEmail()).
                        param("mobileNumber", userEditViewModel.getMobileNumber())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attribute("successfulUpdated", true)).
                andExpect(redirectedUrl("/users/profile"));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postMyProfile_SaveInfo_withChanges_NotExistingEmail_Successfull() throws Exception {

        Long userDetailsId = 1L;

        String newEmail = "different@email.com";

        UserEditViewModel userEditViewModel = new UserEditViewModel().
                setId(userDetailsId).
                setEmail("test@abv.bg").
                setMobileNumber("0888888888").
                setFirstName("Test").
                setLastName("Testov");

        when(userService.isEmailFree(newEmail)).
                thenReturn(true);

        mockMvc.perform(post("/users/profile/save").
                        with(csrf()).
                        param("firstName",userEditViewModel.getFirstName()).
                        param("lastName",userEditViewModel.getLastName()).
                        param("email", newEmail).
                        param("mobileNumber", userEditViewModel.getMobileNumber())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attribute("successfulUpdated", true)).
                andExpect(redirectedUrl("/users/profile"));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postMyProfile_SaveInfo_withChanges_ExistEmail_NotSuccessfull() throws Exception {

        Long userDetailsId = 1L;

        String newEmail = "different@email.com";

        UserEditViewModel userEditViewModel = new UserEditViewModel().
                setId(userDetailsId).
                setEmail("test@abv.bg").
                setMobileNumber("0888888888").
                setFirstName("Test").
                setLastName("Testov");

        when(userService.isEmailFree(newEmail)).
                thenReturn(false);

        mockMvc.perform(post("/users/profile/save").
                        with(csrf()).
                        param("firstName",userEditViewModel.getFirstName()).
                        param("lastName",userEditViewModel.getLastName()).
                        param("email", newEmail).
                        param("mobileNumber", userEditViewModel.getMobileNumber())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attribute("emailIsNotFree", true)).
                andExpect(redirectedUrl("/users/profile/edit"));

    }



    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void getChangePassword_vies() throws Exception {

        mockMvc.perform(get("/users/passwords/change")).
                andExpect(status().isOk()).andExpect(view().name("passwords-change"));
    }



    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postChange_Password_Successfull() throws Exception {

        Long userDetailsId = 1L;


        PasswordChangingBindingModel passwordChangingBindingModel =
                new PasswordChangingBindingModel().
                        setOldPassword("12345").
                        setNewPassword("54321").
                        setNewPasswordConfirm("54321");

        when(userService.isThisIsOldPasswordByUserId(passwordChangingBindingModel.getOldPassword(),userDetailsId)).
                thenReturn(true);

        when(userService.isNewPasswordIsEqualToOldPassByUserId( passwordChangingBindingModel.getNewPassword(),userDetailsId)).
                thenReturn(false);

        mockMvc.perform(post("/users/passwords/change").
                        with(csrf()).
                        param("oldPassword",passwordChangingBindingModel.getOldPassword()).
                        param("newPassword",passwordChangingBindingModel.getNewPassword()).
                        param("newPasswordConfirm", passwordChangingBindingModel.getNewPasswordConfirm())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attribute("successfulUpdated", true)).
                andExpect(redirectedUrl("/users/profile"));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postChange_Password_SamePassword_NotSuccessfull() throws Exception {

        Long userDetailsId = 1L;


        PasswordChangingBindingModel passwordChangingBindingModel =
                new PasswordChangingBindingModel().
                        setOldPassword("12345").
                        setNewPassword("54321").
                        setNewPasswordConfirm("54321");

        when(userService.isThisIsOldPasswordByUserId(passwordChangingBindingModel.getOldPassword(),userDetailsId)).
                thenReturn(true);

        when(userService.isNewPasswordIsEqualToOldPassByUserId( passwordChangingBindingModel.getNewPassword(),userDetailsId)).
                thenReturn(true);

        mockMvc.perform(post("/users/passwords/change").
                        with(csrf()).
                        param("oldPassword",passwordChangingBindingModel.getOldPassword()).
                        param("newPassword",passwordChangingBindingModel.getNewPassword()).
                        param("newPasswordConfirm", passwordChangingBindingModel.getNewPasswordConfirm())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attribute("isNewPasswordIsEqualToOldPass", true)).
                andExpect(redirectedUrl("/users/passwords/change"));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postChange_Password_NotEqualsPasswords_NotSuccessfull() throws Exception {

        Long userDetailsId = 1L;


        PasswordChangingBindingModel passwordChangingBindingModel =
                new PasswordChangingBindingModel().
                        setOldPassword("12345").
                        setNewPassword("54321").
                        setNewPasswordConfirm("12345");

        when(userService.isThisIsOldPasswordByUserId(passwordChangingBindingModel.getOldPassword(),userDetailsId)).
                thenReturn(true);

        when(userService.isNewPasswordIsEqualToOldPassByUserId( passwordChangingBindingModel.getNewPassword(),userDetailsId)).
                thenReturn(false);

        mockMvc.perform(post("/users/passwords/change").
                        with(csrf()).
                        param("oldPassword",passwordChangingBindingModel.getOldPassword()).
                        param("newPassword",passwordChangingBindingModel.getNewPassword()).
                        param("newPasswordConfirm", passwordChangingBindingModel.getNewPasswordConfirm())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attribute("passwordsNotMach", true)).
                andExpect(redirectedUrl("/users/passwords/change"));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postChange_Password_NotMatchOldPassword_NotSuccessfull() throws Exception {

        Long userDetailsId = 1L;


        PasswordChangingBindingModel passwordChangingBindingModel =
                new PasswordChangingBindingModel().
                        setOldPassword("12345").
                        setNewPassword("54321").
                        setNewPasswordConfirm("54321");

        when(userService.isThisIsOldPasswordByUserId(passwordChangingBindingModel.getOldPassword(),userDetailsId)).
                thenReturn(false);

        when(userService.isNewPasswordIsEqualToOldPassByUserId( passwordChangingBindingModel.getNewPassword(),userDetailsId)).
                thenReturn(false);

        mockMvc.perform(post("/users/passwords/change").
                        with(csrf()).
                        param("oldPassword",passwordChangingBindingModel.getOldPassword()).
                        param("newPassword",passwordChangingBindingModel.getNewPassword()).
                        param("newPasswordConfirm", passwordChangingBindingModel.getNewPasswordConfirm())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attribute("isThisNotOldPassword", true)).
                andExpect(redirectedUrl("/users/passwords/change"));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postChange_Password_ShortNewPassword_WithTwoSymbols_NotSuccessfull() throws Exception {

        Long userDetailsId = 1L;

        PasswordChangingBindingModel passwordChangingBindingModel =
                new PasswordChangingBindingModel().
                        setOldPassword("12345").
                        setNewPassword("54").
                        setNewPasswordConfirm("54");

        when(userService.isThisIsOldPasswordByUserId(passwordChangingBindingModel.getOldPassword(),userDetailsId)).
                thenReturn(true);

        when(userService.isNewPasswordIsEqualToOldPassByUserId( passwordChangingBindingModel.getNewPassword(),userDetailsId)).
                thenReturn(false);

        mockMvc.perform(post("/users/passwords/change").
                        with(csrf()).
                        param("oldPassword",passwordChangingBindingModel.getOldPassword()).
                        param("newPassword",passwordChangingBindingModel.getNewPassword()).
                        param("newPasswordConfirm", passwordChangingBindingModel.getNewPasswordConfirm())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/passwords/change"));

    }


    @Test
    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    void postChange_Password_LongNewPassword_With21Symbols_NotSuccessfull() throws Exception {

        Long userDetailsId = 1L;

        PasswordChangingBindingModel passwordChangingBindingModel =
                new PasswordChangingBindingModel().
                        setOldPassword("12345").
                        setNewPassword("123456789qwertyuiopas").
                        setNewPasswordConfirm("123456789qwertyuiopas");

        when(userService.isThisIsOldPasswordByUserId(passwordChangingBindingModel.getOldPassword(),userDetailsId)).
                thenReturn(true);

        when(userService.isNewPasswordIsEqualToOldPassByUserId( passwordChangingBindingModel.getNewPassword(),userDetailsId)).
                thenReturn(false);

        mockMvc.perform(post("/users/passwords/change").
                        with(csrf()).
                        param("oldPassword",passwordChangingBindingModel.getOldPassword()).
                        param("newPassword",passwordChangingBindingModel.getNewPassword()).
                        param("newPasswordConfirm", passwordChangingBindingModel.getNewPasswordConfirm())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/users/passwords/change"));

    }

}
