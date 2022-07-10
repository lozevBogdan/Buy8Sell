package com.example.sellbuy.model.view.productViews;

import com.example.sellbuy.model.entity.*;

import java.util.HashSet;
import java.util.Set;

public class ProductViewModel extends BaseProductViewModel {

    private Set<PictureEntity> pictures = new HashSet<>();
    private Set<CommentEntity> comments= new HashSet<>();
    private Set<UserEntity> fans= new HashSet<>();
    private int views;


    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public ProductViewModel setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public ProductViewModel setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public Set<UserEntity> getFans() {
        return fans;
    }

    public ProductViewModel setFans(Set<UserEntity> fans) {
        this.fans = fans;
        return this;
    }

    public int getViews() {
        return views;
    }

    public ProductViewModel setViews(int views) {
        this.views = views;
        return this;
    }
}
