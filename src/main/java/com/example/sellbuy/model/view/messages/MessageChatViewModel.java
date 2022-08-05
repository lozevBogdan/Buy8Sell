package com.example.sellbuy.model.view.messages;

import com.example.sellbuy.model.view.userViews.UserChatViewModel;

import java.time.LocalDateTime;

public class MessageChatViewModel {
    private LocalDateTime created;
    private UserChatViewModel sender;
    private String message;

    public MessageChatViewModel( ) {

    }

    public LocalDateTime getCreated() {
        return created;
    }

    public MessageChatViewModel setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public UserChatViewModel getSender() {
        return sender;
    }

    public MessageChatViewModel setSender(UserChatViewModel sender) {
        this.sender = sender;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageChatViewModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
