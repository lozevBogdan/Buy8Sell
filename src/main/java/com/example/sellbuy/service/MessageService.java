package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.MessageBindingModel;
import com.example.sellbuy.model.entity.MessageEntity;
import com.example.sellbuy.model.view.messages.MessageChatViewModel;

import java.util.Set;

public interface MessageService {
    MessageEntity addInDb(MessageEntity message);

    MessageEntity createAndSave(MessageBindingModel messageBindingModel, Long productId, Long receiverId, Long currentUserId);


    Set<MessageEntity> getMessageBySenderAndReceiver(Long senderId, Long receiverId);

    void deleteByProductId(Long id);

    Set<MessageChatViewModel> findChatsMessagesByProductIdSenderIdReceiverId(Long productId, Long senderId, Long currentUsedId);
}
