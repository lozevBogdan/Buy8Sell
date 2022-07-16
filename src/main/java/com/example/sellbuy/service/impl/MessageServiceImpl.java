package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.binding.MessageBindingModel;
import com.example.sellbuy.model.entity.MessageEntity;
import com.example.sellbuy.model.entity.UserEntity;
import com.example.sellbuy.repository.MessageRepository;
import com.example.sellbuy.service.MessageService;
import com.example.sellbuy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MessageServiceImpl  implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Override
    public MessageEntity addInDb(MessageEntity message) {
        return this.messageRepository.save(message);
    }

    @Override
    public MessageEntity createAndSave(MessageBindingModel messageBindingModel, Long productId, Long receiverId, Long currentUserId) {
        UserEntity sender = this.userService.findById(currentUserId);
        UserEntity receiver = this.userService.findById(receiverId);
        MessageEntity message = new MessageEntity();
        message.
                setMessage(messageBindingModel.getMessage()).
                setReceiver(receiver).setSender(sender);
        message = this.messageRepository.save(message);
        return message;
    }

    @Override
    public Set<MessageEntity> getMessageBySenderAndReceiver(Long senderId, Long receiverId) {

        return this.messageRepository.findBySenderIdAndReceiverId(senderId,receiverId);

    }

}
