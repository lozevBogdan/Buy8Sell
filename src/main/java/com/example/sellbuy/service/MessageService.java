package com.example.sellbuy.service;

import com.example.sellbuy.model.binding.MessageBindingModel;
import com.example.sellbuy.model.entity.MessageEntity;

public interface MessageService {
    MessageEntity addInDb(MessageEntity message);

    MessageEntity createAndSave(MessageBindingModel messageBindingModel, Long productId, Long receiverId, Long currentUserId);
}
