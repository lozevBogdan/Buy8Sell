package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.MessageEntity;
import com.example.sellbuy.repository.MessageRepository;
import com.example.sellbuy.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl  implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageEntity addInDb(MessageEntity message) {
        return this.messageRepository.save(message);
    }
}
