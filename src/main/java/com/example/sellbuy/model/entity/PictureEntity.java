package com.example.sellbuy.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

    @Column
    @Lob
    private String url;

    @OneToOne(mappedBy = "picture",cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductEntity product;

    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public PictureEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }
}
