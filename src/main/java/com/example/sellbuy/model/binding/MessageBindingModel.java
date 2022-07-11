package com.example.sellbuy.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MessageBindingModel {

    @Size(min = 3)
    private String message;

    public String getMessage() {
        return message;
    }

    public MessageBindingModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
