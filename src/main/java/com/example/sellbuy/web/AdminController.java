package com.example.sellbuy.web;

import com.example.sellbuy.model.exception.ObjectNotFoundException;
import com.example.sellbuy.model.view.statisticViews.StatisticViewModel;
import com.example.sellbuy.model.view.userViews.UserInfoViewModel;
import com.example.sellbuy.service.StatisticService;
import com.example.sellbuy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final StatisticService statisticService;

    public AdminController(UserService userService, StatisticService statisticService) {
        this.userService = userService;
        this.statisticService = statisticService;
    }

    @GetMapping("/users")
    public String allUsers(Model model){
        List<UserInfoViewModel> allUsersViewModels = this.userService.getAllUsers();
        model.addAttribute("allUsersViewModels",allUsersViewModels);
        return "admin-all-users";
    }

    @GetMapping("/statistic")
    public String getStatistic(Model model){
        StatisticViewModel statisticInfo = this.statisticService.getStatisticInfo();
        model.addAttribute("statisticInfo",statisticInfo);

        return "admin-statistic";
    }

    @GetMapping("/users/edit/{userId}")
    public String getInfoForEdit(Model model, @PathVariable Long userId){

        UserInfoViewModel userInfoViewModel = this.userService.getUserInfoViewModelByUserId(userId);

        if(userInfoViewModel == null){
            throw new ObjectNotFoundException(userId,"User");
        }

        if(!model.containsAttribute("userInfoViewModel")){
            model.addAttribute("userInfoViewModel",userInfoViewModel);
        }
        return "admin-user-edit";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({ObjectNotFoundException.class})
    public ModelAndView onObjectNotFound(ObjectNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("object-not-found");
        modelAndView.addObject("objectId", onfe.getObjectId());
        modelAndView.addObject("typeOfObject", onfe.getTypeOfObject());
        return modelAndView;
    }

    @PostMapping("/users/save/{userId}")
    public String editInfoByUserId(@Valid UserInfoViewModel userInfoViewModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam(defaultValue = "false") boolean isAdmin,
                                   @PathVariable Long userId){

        boolean emailIsFree = true;
        System.out.println();
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
