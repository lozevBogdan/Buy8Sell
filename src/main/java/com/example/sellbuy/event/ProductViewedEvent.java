package com.example.sellbuy.event;

import org.springframework.context.ApplicationEvent;

public class ProductViewedEvent extends ApplicationEvent {


    private Long productId;

    public ProductViewedEvent(Object source, Long productId) {
        super(source);
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public ProductViewedEvent setProductId(Long productId) {
        this.productId = productId;
        return this;
    }
}
