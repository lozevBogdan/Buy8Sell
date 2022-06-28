package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.UserLoginBindingModel;
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
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){


//todo thi shoud be replece with Spring security propurties

//        if(this.userService.isUserLoggedIn()){
//            return "redirect:home";
//        }

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

    @PostMapping("/login")
    public String login(@Valid UserLoginBindingModel userLoginBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

            boolean isCredentialsAreValid =
                    this.userService.
                            isExistUserWithEmailAndPassword(userLoginBindingModel.getEmail(),
                                    userLoginBindingModel.getPassword());

        if (bindingResult.hasErrors() || isCredentialsAreValid) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("invalidCredentials", isCredentialsAreValid);

            return "redirect:/users/login";
        }


        userService.loginUser(userLoginBindingModel);

        return "redirect:/";
    }

    @GetMapping("/favorites")
    public String favorites(){
        return "favorites-products";
    }

    @GetMapping("/messages")
    public String messages(){
        return "chats-all";
    }


    @GetMapping("/logout")
    public String logout(){
        this.userService.logoutCurrentUser();
        return  "redirect:/";
    }

}
