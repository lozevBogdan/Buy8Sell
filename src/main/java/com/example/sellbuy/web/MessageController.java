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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;

    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/all/user/{id}")
    public String messages(@PathVariable Long id, Model model){

        Set<UserEntity> chatsUsers = new HashSet<>();

        UserEntity user = this.userService.findById(id);

        Set<MessageEntity> receivedMessages = user.getReceiverMessages();
        Set<MessageEntity> sendMessages = user.getSendMessages();

        for (MessageEntity receiveMessage : receivedMessages) {
            chatsUsers.add(receiveMessage.getSender());
        }

        for (MessageEntity sendMessage : sendMessages) {
            chatsUsers.add(sendMessage.getReceiver());
        }

        //todo: to make a chatUsers dto modelt
        model.addAttribute("chatsUsers",chatsUsers);


        return "chats-all";
    }

    @GetMapping("chats/user/{id}")
    public String getChatMessages(@PathVariable Long id,
                                  @AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser,
                                  Model model){

        Set<MessageEntity> receivedMessages =
                this.messageService.getMessageBySenderAndReceiver(id,sellAndBuyUser.getId());
        Set<MessageEntity> sendedMessages =
                this.messageService.getMessageBySenderAndReceiver(sellAndBuyUser.getId(),id);

        Set<MessageEntity> allMessages = new HashSet<>();
        allMessages.addAll(receivedMessages);
        allMessages.addAll(sendedMessages);

        allMessages.stream().sorted((a,b)->a.getCreated().compareTo(b.getCreated()));


        model.addAttribute("allMessages",allMessages);

        return "chats-all";
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
