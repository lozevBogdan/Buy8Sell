package com.example.sellbuy.model.view.productViews;

import com.example.sellbuy.model.entity.PictureEntity;

public class ProductChatViewModel {
    private Long id;
    private String title;
    private PictureEntity picture;

    public ProductChatViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ProductChatViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductChatViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public ProductChatViewModel setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }
}
