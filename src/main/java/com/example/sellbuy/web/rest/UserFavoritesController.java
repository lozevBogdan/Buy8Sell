package com.example.sellbuy.web.rest;

import com.example.sellbuy.model.entity.ProductEntity;
import com.example.sellbuy.model.view.messages.MessageChatViewModel;
import com.example.sellbuy.model.view.productViews.ProductFavoriteViewModel;
import com.example.sellbuy.model.view.productViews.ProductSearchViewModel;
import com.example.sellbuy.securityUser.SellAndBuyUserDetails;
import com.example.sellbuy.service.MessageService;
import com.example.sellbuy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserFavoritesController {

    private final UserService userService;

    public UserFavoritesController(UserService userService) {
        this.userService = userService;
    }

    //    @GetMapping("/{routeId}/comments")
//    public ResponseEntity<List<CommentDisplayView>> getComments(@PathVariable("routeId") Long routeId) {
//        return ResponseEntity.ok(commentService.getAllCommentsForRoute(routeId));
//    }


    @GetMapping("/{id}/favorites")
    public ResponseEntity<List<ProductFavoriteViewModel>> getAllFavorites(@PathVariable Long id, Model model) {

        Set<ProductEntity> favorList = this.userService.getFavorListOf(id);

        List<ProductFavoriteViewModel> productSearchViewModelList =
                this.userService.returnFavors(favorList, id);



        return ResponseEntity.ok(productSearchViewModelList);
    }


//    @GetMapping("/send/{senderId}/{productId}")
//    public String getChatMessagesFromSender(@AuthenticationPrincipal SellAndBuyUserDetails sellAndBuyUser,
//                                            Model model, @PathVariable Long productId, @PathVariable Long senderId){
//
//        Set<MessageChatViewModel> allMessages = this.messageService.
//                findChatsMessagesByProductIdSenderIdReceiverId(productId,senderId,sellAndBuyUser.getId());
//
//        model.addAttribute("allMessages",allMessages);
//        model.addAttribute("chatWitUserId",senderId);
//
//        return "messages-for-product";
//    }
}
