package com.example.sellbuy.model.view;

import com.example.sellbuy.model.entity.*;
import com.example.sellbuy.model.entity.enums.ConditionEnum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
