package com.example.sellbuy.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column(nullable = false)
    private String textContent;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private ProductEntity productEntity;

    private boolean isApproved = false;

    public CommentEntity() {
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentEntity setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public ProductEntity getProduct() {
        return productEntity;
    }

    public CommentEntity setProduct(ProductEntity product) {
        this.productEntity = product;
        return this;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public CommentEntity setApproved(boolean approved) {
        isApproved = approved;
        return this;
    }
}
