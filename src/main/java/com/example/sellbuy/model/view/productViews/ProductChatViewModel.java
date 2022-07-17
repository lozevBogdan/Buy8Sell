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

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }
}
