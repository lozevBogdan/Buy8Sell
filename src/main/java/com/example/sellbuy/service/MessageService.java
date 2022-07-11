package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.MessageEntity;

public interface MessageService {
    MessageEntity addInDb(MessageEntity message);
}
