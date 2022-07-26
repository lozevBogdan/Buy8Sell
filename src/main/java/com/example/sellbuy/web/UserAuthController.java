package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.UserRegisterBindingModel;
import com.example.sellbuy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserAuthController {

    private final UserService userService;

    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute("email") String email,
                                RedirectAttributes redirectAttributes
    ){
        redirectAttributes.addFlashAttribute("email",email);
        redirectAttributes.addFlashAttribute("invalidCredentials",true);

        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        boolean isEmailFree =
                this.userService.isEmailFree(userRegisterBindingModel.getEmail());

        boolean passwordsAreEquals = userRegisterBindingModel.getPassword().
                equals(userRegisterBindingModel.getConfirmPassword());

        if (bindingResult.hasErrors() || !isEmailFree || !passwordsAreEquals) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("emailIsNotFree", !isEmailFree);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute(
                    "passwordsNotMach",!passwordsAreEquals);
            return "redirect:/users/register";
        }
        userService.makeNewRegistration(userRegisterBindingModel);
        return "redirect:login";
    }

}
