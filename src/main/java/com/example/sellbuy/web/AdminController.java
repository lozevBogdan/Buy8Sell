package com.example.sellbuy.web;

import com.example.sellbuy.model.view.userViews.UserInfoViewModel;
import com.example.sellbuy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String allUsers(Model model){
        List<UserInfoViewModel> allUsersViewModels = this.userService.getAllUsers();
        model.addAttribute("allUsersViewModels",allUsersViewModels);
        return "admin-all-users";
    }

    @GetMapping("/users/edit/{userId}")
    public String getInfoForEdit(Model model, @PathVariable Long userId){

        UserInfoViewModel userInfoViewModel = this.userService.getUserInfoViewModelByUserId(userId);
        model.addAttribute("userInfoViewModel",userInfoViewModel);

        return "admin-user-edit";

    }

}
