package com.example.sellbuy.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentBindingDto {

    @NotEmpty
    private String textContent;

    public String getTextContent() {
        return textContent;
    }

    public CommentBindingDto setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }
}
