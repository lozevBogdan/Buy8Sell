package com.example.sellbuy.model.view.productViews;

import java.math.BigDecimal;

public class ProductFavoriteViewModel {

    private Long id;
    private String pictureUrl;
    private String title;
    private BigDecimal price;
    private boolean isPromo;

    public Long getId() {
        return id;
    }

    public ProductFavoriteViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public ProductFavoriteViewModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ProductFavoriteViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductFavoriteViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public ProductFavoriteViewModel setPromo(boolean promo) {
        isPromo = promo;
        return this;
    }
}
