package com.example.sellbuy.model.view.productViews;

public class ProductSearchViewModel extends BaseProductViewModel {

    private String mainPicture;

    public String getMainPicture() {
        return mainPicture;
    }

    public ProductSearchViewModel setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
        return this;
    }
}
