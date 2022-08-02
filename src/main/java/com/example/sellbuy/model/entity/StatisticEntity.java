package com.example.sellbuy.model.entity;

import org.springframework.data.annotation.TypeAlias;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "statistics")
public class StatisticEntity extends BaseEntity {


    private Long authenticatedRequests;

    private Long anonymousRequests;

    private Long indexPageViews;

    private Long allProductsPageViews;

    private Long loginPageViews;

    private Long registerPageViews;

    private Long promotionsPageViews;

    private Long messagesPageViews;

    private Long favoritesPageViews;

    private Long myProfilePageViews;

    private Long myProductsPageViews;

    private Long addProductPageViews;

    private Long userInSystemPageViews;

    private Long statisticsPageViews;


    public StatisticEntity() {
    }

    public Long getAuthenticatedRequests() {
        return authenticatedRequests;
    }

    public void setAuthenticatedRequests(Long authenticatedRequests) {
        this.authenticatedRequests = authenticatedRequests;
    }

    public Long getAnonymousRequests() {
        return anonymousRequests;
    }

    public void setAnonymousRequests(Long anonymousRequests) {
        this.anonymousRequests = anonymousRequests;
    }

    public Long getIndexPageViews() {
        return indexPageViews;
    }

    public void setIndexPageViews(Long indexPageViews) {
        this.indexPageViews = indexPageViews;
    }

    public Long getAllProductsPageViews() {
        return allProductsPageViews;
    }

    public void setAllProductsPageViews(Long allProductsPageViews) {
        this.allProductsPageViews = allProductsPageViews;
    }

    public Long getLoginPageViews() {
        return loginPageViews;
    }

    public void setLoginPageViews(Long loginPageViews) {
        this.loginPageViews = loginPageViews;
    }

    public Long getRegisterPageViews() {
        return registerPageViews;
    }

    public void setRegisterPageViews(Long registerPageViews) {
        this.registerPageViews = registerPageViews;
    }

    public Long getPromotionsPageViews() {
        return promotionsPageViews;
    }

    public void setPromotionsPageViews(Long promotionsPageViews) {
        this.promotionsPageViews = promotionsPageViews;
    }

    public Long getMessagesPageViews() {
        return messagesPageViews;
    }

    public void setMessagesPageViews(Long messagesPageViews) {
        this.messagesPageViews = messagesPageViews;
    }

    public Long getFavoritesPageViews() {
        return favoritesPageViews;
    }

    public void setFavoritesPageViews(Long favoritesPageViews) {
        this.favoritesPageViews = favoritesPageViews;
    }

    public Long getMyProfilePageViews() {
        return myProfilePageViews;
    }

    public void setMyProfilePageViews(Long myProfilePageViews) {
        this.myProfilePageViews = myProfilePageViews;
    }

    public Long getMyProductsPageViews() {
        return myProductsPageViews;
    }

    public void setMyProductsPageViews(Long myProductsPageViews) {
        this.myProductsPageViews = myProductsPageViews;
    }

    public Long getAddProductPageViews() {
        return addProductPageViews;
    }

    public void setAddProductPageViews(Long addProductPageViews) {
        this.addProductPageViews = addProductPageViews;
    }

    public Long getUserInSystemPageViews() {
        return userInSystemPageViews;
    }

    public void setUserInSystemPageViews(Long userInSystemPageViews) {
        this.userInSystemPageViews = userInSystemPageViews;
    }

    public Long getStatisticsPageViews() {
        return statisticsPageViews;
    }

    public void setStatisticsPageViews(Long statisticsPageViews) {
        this.statisticsPageViews = statisticsPageViews;
    }
}
