package com.example.sellbuy.web;

import com.example.sellbuy.model.binding.MessageBindingModel;
import com.example.sellbuy.model.entity.MessageEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.service.MessageService;
import com.example.sellbuy.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;

    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }


    @PostMapping("/send/{receiverId}/{productId}")
    public String sentMessage(@Valid MessageBindingModel messageBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, @PathVariable Long productId,
                              @PathVariable Long receiverId,
                              @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageBindingModel", messageBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.messageBindingModel",
                    bindingResult);
            return "redirect:/products/info/" + productId ;
        }

        //todo: refactor like move in message service
        MessageEntity message =
                this.messageService.
                        createAndSave(messageBindingModel, productId, receiverId, sellAndBuyUser.getId());

        redirectAttributes.addFlashAttribute("isSend", true);
        return "redirect:/products/info/" + productId ;
    }



}
