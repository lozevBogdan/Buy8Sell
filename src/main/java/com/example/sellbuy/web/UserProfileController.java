package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.PasswordChangingBindingModel;
import com.example.sellbuy.model.binding.UserLoginBindingModel;
import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.model.view.userViews.UserEditViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.service.ProductService;
import com.example.sellbuy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public PasswordChangingBindingModel passwordChangingBindingModel(){
        return new PasswordChangingBindingModel();
    }

    @GetMapping("/profile/{id}")
    public String myProfile(@PathVariable Long id,Model model){
        UserEditViewModel  userEditViewModel = this.userService.findByIdUserEditViewModel(id);
        model.addAttribute("userEditViewModel",userEditViewModel);
        return "my-profile";
    }

    @GetMapping("/profile/{userId}/edit")
    public String editUserInfo(@PathVariable Long userId, Model model){

        if(!model.containsAttribute("userEditViewModel")) {
            UserEditViewModel userEditViewModel = this.userService.findByIdUserEditViewModel(userId);
            model.addAttribute("userEditViewModel", userEditViewModel);
        }

        return "my-profile-edit";
    }

    @PostMapping("/profile/{userId}/save")
    public String register(@Valid UserEditViewModel userEditViewModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @PathVariable Long userId,
                           @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        boolean isEmailFree = true;

        if(!sellAndBuyUser.getUsername().equals(userEditViewModel.getEmail())){
            isEmailFree = this.userService.isEmailFree(userEditViewModel.getEmail());
        }

        if (bindingResult.hasErrors() || !isEmailFree ) {
            redirectAttributes.addFlashAttribute("userEditViewModel", userEditViewModel);
            redirectAttributes.addFlashAttribute("emailIsNotFree", !isEmailFree);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userEditViewModel", bindingResult);

            return "redirect:/users/profile/" + userId + "/edit";
        }
        userService.updateUserByIdWithUserEditViewModel(userId,userEditViewModel);
        redirectAttributes.addFlashAttribute("successfulUpdated",true);
        return "redirect:/users/profile/" + userId ;
    }

    @GetMapping("/passwords/change")
    public String changePassword(){
        return "passwords-change";
    }

    @PostMapping("/passwords/change")
    public String changePassword(@Valid PasswordChangingBindingModel passwordChangingBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser
                                 ){
        boolean passwordsAreEquals = passwordChangingBindingModel.getNewPassword().
                equals(passwordChangingBindingModel.getNewPasswordConfirm());

        boolean isThisOldPassword;

        if(passwordChangingBindingModel.getOldPassword().isEmpty()){
            isThisOldPassword =false;
        }else {
             isThisOldPassword =
                    this.userService.
                            isThisIsOldPasswordByUserId(passwordChangingBindingModel.getOldPassword(), sellAndBuyUser.getId());
        }

        boolean isNewPasswordIsEqualToOldPass = false;

        if(isThisOldPassword && passwordsAreEquals){
            isNewPasswordIsEqualToOldPass =
                    this.userService.
                            isNewPasswordIsEqualToOldPassByUserId(
                                    passwordChangingBindingModel.getNewPassword(),sellAndBuyUser.getId());
        }

        if (bindingResult.hasErrors() || !passwordsAreEquals || !isThisOldPassword || isNewPasswordIsEqualToOldPass) {

            redirectAttributes.addFlashAttribute("passwordChangingBindingModel", passwordChangingBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.passwordChangingBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("passwordsNotMach",!passwordsAreEquals);
            redirectAttributes.addFlashAttribute("isThisNotOldPassword", !isThisOldPassword);
            redirectAttributes.addFlashAttribute("isNewPasswordIsEqualToOldPass", isNewPasswordIsEqualToOldPass);


            return "redirect:/users/passwords/change";
        }

        this.userService.changePasswordByUserId(passwordChangingBindingModel.getNewPassword(),sellAndBuyUser.getId());

        redirectAttributes.addFlashAttribute("successfulUpdated",true);

        return "redirect:/users/profile/" + sellAndBuyUser.getId() ;
    }
}
