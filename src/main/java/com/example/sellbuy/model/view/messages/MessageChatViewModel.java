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

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public UserChatViewModel getSender() {
        return sender;
    }

    public void setSender(UserChatViewModel sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
