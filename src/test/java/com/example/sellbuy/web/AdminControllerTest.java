package com.example.sellbuy.web;

import com.example.sellbuy.init.TestDataInit;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.model.entity.UserRoleEntity;
import com.example.sellbuy.model.view.userViews.UserInfoViewModel;
import com.example.sellbuy.service.StatisticService;
import com.example.sellbuy.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataInit testDataInit;

    @MockBean
     UserService userService;

    @MockBean
    StatisticService statisticService;

    @AfterEach
    void tearDown(){
        this.testDataInit.cleanUpDatabase();
    }


    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    @Test
    void loadAllUsersInSystem() throws Exception {

        UserEntity testAdmin = testDataInit.createTestAdmin("admin@abv.bg");
        UserEntity testUser = testDataInit.createTestUser("testUser@abv.bg");

        UserInfoViewModel userInfoViewModelAdmin =  new UserInfoViewModel();
        UserInfoViewModel userInfoViewModelUser =  new UserInfoViewModel();

        userInfoViewModelAdmin.
                setAdmin(true).
                setId(testAdmin.getId()).
                setRoles(mapToList(testAdmin.getRoles())).
                setEmail(testAdmin.getEmail()).
                setFirstName(testAdmin.getFirstName()).
                setModified(testAdmin.getModified()).
                setCreated(testAdmin.getCreated()).
                setMobileNumber(testAdmin.getMobileNumber());

        userInfoViewModelUser.
                setAdmin(false).
                setId(testUser.getId()).
                setRoles(mapToList(testUser.getRoles())).
                setEmail(testUser.getEmail()).
                setFirstName(testUser.getFirstName()).
                setModified(testUser.getModified()).
                setCreated(testUser.getCreated()).
                setMobileNumber(testUser.getMobileNumber());

        when(userService.getAllUsers()).
                thenReturn(List.of(userInfoViewModelAdmin,userInfoViewModelUser));

        mockMvc.perform(get("/admin/users")).
                andExpect(status().isOk()).
                     andExpect(model().attribute("allUsersViewModels",
                             List.of(userInfoViewModelAdmin,userInfoViewModelUser))).
                        andExpect((view().name("admin-all-users")));

    }

    private List<UserRoleEntity> mapToList(Set<UserRoleEntity> incomeSet){
        List<UserRoleEntity> returnedList = new LinkedList<>();
        returnedList.addAll(incomeSet);
        return returnedList;
    }

    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    @Test
    void loadInfoForEditUserProfile() throws Exception {

        UserEntity testUser = testDataInit.createTestUser("testUser@abv.bg");

        UserInfoViewModel userInfoViewModelUser =  new UserInfoViewModel().
                setAdmin(false).
                setId(testUser.getId()).
                setRoles(mapToList(testUser.getRoles())).
                setEmail(testUser.getEmail()).
                setFirstName(testUser.getFirstName()).
                setModified(testUser.getModified()).
                setCreated(testUser.getCreated()).
                setMobileNumber(testUser.getMobileNumber());

        when(userService.getUserInfoViewModelByUserId(testUser.getId())).
                thenReturn(userInfoViewModelUser);

        mockMvc.perform(get("/admin/users/edit/" + testUser.getId())).
                andExpect(status().isOk()).
                andExpect(model().attribute("userInfoViewModel", userInfoViewModelUser)).
                andExpect((view().name("admin-user-edit")));

    }

    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    @Test
    void loadInfoForEditUserProfile_throw_ObjectNotFoundException() throws Exception {

        UserEntity testUser = testDataInit.createTestUser("testUser@abv.bg");

        UserInfoViewModel userInfoViewModelUser =  new UserInfoViewModel().
                setAdmin(false).
                setId(testUser.getId()).
                setRoles(mapToList(testUser.getRoles())).
                setEmail(testUser.getEmail()).
                setFirstName(testUser.getFirstName()).
                setModified(testUser.getModified()).
                setCreated(testUser.getCreated()).
                setMobileNumber(testUser.getMobileNumber());

        when(userService.getUserInfoViewModelByUserId(testUser.getId())).
                thenReturn(null);

        mockMvc.perform(get("/admin/users/edit/" + testUser.getId())).
                andExpect(status().isNotFound()).
                andExpect((view().name("object-not-found")));

    }


    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    @Test
    void updateUserInformation_Successfull() throws Exception {

        UserEntity testUser = testDataInit.createTestUser("testUser@abv.bg");

        UserInfoViewModel userInfoViewModelUser =  new UserInfoViewModel().
                setAdmin(false).
                setId(testUser.getId()).
                setRoles(mapToList(testUser.getRoles())).
                setEmail(testUser.getEmail()).
                setFirstName(testUser.getFirstName()).
                setLastName(testUser.getLastName()).
                setModified(testUser.getModified()).
                setCreated(testUser.getCreated()).
                setMobileNumber(testUser.getMobileNumber());

        when(userService.getUserInfoViewModelByUserId(testUser.getId())).
                thenReturn(userInfoViewModelUser);

//        when(userService.isEmailFree(userInfoViewModelUser.getEmail())).
//                thenReturn(true);

        mockMvc.perform(post("/admin/users/save/" + testUser.getId()).
                        with(csrf()).
                        param("firstName",userInfoViewModelUser.getFirstName()).
                        param("lastName",userInfoViewModelUser.getLastName()).
                        param("email",userInfoViewModelUser.getEmail()).
                        param("mobileNumber",userInfoViewModelUser.getMobileNumber())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/users"));

    }


    @WithUserDetails(value = "test@abv.bg",
            userDetailsServiceBeanName = "testUserDetailsService")
    @Test
    void updateUserInformation_withExistingEmail_NotSuccessfull() throws Exception {

        UserEntity testAdmin = testDataInit.createTestAdmin("admin@abv.bg");
        UserEntity testUser = testDataInit.createTestUser("testUser@abv.bg");

        UserInfoViewModel userInfoViewModelAdmin =  new UserInfoViewModel() .
                setAdmin(true).
                setId(testAdmin.getId()).
                setRoles(mapToList(testAdmin.getRoles())).
                setEmail(testAdmin.getEmail()).
                setFirstName(testAdmin.getFirstName()).
                setModified(testAdmin.getModified()).
                setCreated(testAdmin.getCreated()).
                setMobileNumber(testAdmin.getMobileNumber());

        UserInfoViewModel userInfoViewModelUser =  new UserInfoViewModel().
                setAdmin(false).
                setId(testUser.getId()).
                setRoles(mapToList(testUser.getRoles())).
                setEmail(testUser.getEmail()).
                setFirstName(testUser.getFirstName()).
                setLastName(testUser.getLastName()).
                setModified(testUser.getModified()).
                setCreated(testUser.getCreated()).
                setMobileNumber(testUser.getMobileNumber());

        when(userService.getUserInfoViewModelByUserId(testUser.getId())).
                thenReturn(userInfoViewModelAdmin);

        when(userService.isEmailFree(userInfoViewModelAdmin.getEmail())).
                thenReturn(false);

        mockMvc.perform(post("/admin/users/save/" + testUser.getId()).
                        with(csrf()).
                        param("firstName",userInfoViewModelUser.getFirstName()).
                        param("lastName",userInfoViewModelUser.getLastName()).
                        param("email",userInfoViewModelUser.getEmail()).
                        param("mobileNumber",userInfoViewModelUser.getMobileNumber())

                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/admin/users/edit/" + testUser.getId()));

    }

}
