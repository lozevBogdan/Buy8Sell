package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.ProductSearchingBindingModel;
import com.example.sellbuy.model.view.userViews.UserInfoViewModel;
import com.example.sellbuy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
        if(!model.containsAttribute("userInfoViewModel")){
            model.addAttribute("userInfoViewModel",userInfoViewModel);
        }
        return "admin-user-edit";
    }

    @PostMapping("/users/save/{userId}")
    public String editInfoByUserId(@Valid UserInfoViewModel userInfoViewModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes, Model model,
                                   @RequestParam(defaultValue = "false") boolean isAdmin,
                                   @PathVariable Long userId){

        boolean emailIsFree = true;

        if(!userInfoViewModel.getEmail().equals(this.userService.getUserInfoViewModelByUserId(userId).getEmail())){
            emailIsFree = this.userService.isEmailFree(userInfoViewModel.getEmail());
        }

        if (bindingResult.hasErrors() || !emailIsFree) {
            userInfoViewModel.setRoles(this.userService.getUserInfoViewModelByUserId(userId).getRoles());
            userInfoViewModel.setId(this.userService.getUserInfoViewModelByUserId(userId).getId());
            redirectAttributes.addFlashAttribute("userInfoViewModel", userInfoViewModel);
            redirectAttributes.addFlashAttribute("emailIsNotFree", !emailIsFree);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userInfoViewModel", bindingResult);

            return "redirect:/admin/users/edit/" + userId;
        }
        this.userService.updateUserByIdWithUserInfoViewModelAndIsAmin(userId,userInfoViewModel,isAdmin);
        redirectAttributes.addFlashAttribute("successfulUpdated",true);
        return "redirect:/admin/users";
    }
}
