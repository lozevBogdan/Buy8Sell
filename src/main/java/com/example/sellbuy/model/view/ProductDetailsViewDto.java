package com.example.sellbuy.model.view;

import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.ConditionEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProductDetailsViewDto extends BaseProductViewModel {

    private Set<PictureEntity> pictures = new HashSet<>();
    private Set<CommentEntity> comments= new HashSet<>();
    private Set<UserEntity> fans= new HashSet<>();
    private int views;
    private LocalDateTime created;
    private LocalDateTime modified;


    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public ProductDetailsViewDto setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public ProductDetailsViewDto setComments(Set<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public Set<UserEntity> getFans() {
        return fans;
    }

    public ProductDetailsViewDto setFans(Set<UserEntity> fans) {
        this.fans = fans;
        return this;
    }

    public int getViews() {
        return views;
    }

    public ProductDetailsViewDto setViews(int views) {
        this.views = views;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ProductDetailsViewDto setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public ProductDetailsViewDto setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }
}
