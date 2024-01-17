package com.foodwallet.server.api.service.basket.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BasketResponse {

    private final Long basketId;
    private final String storeName;
    private final int totalPrice;
    private final List<BasketMenuResponse> menus;

    @Builder
    private BasketResponse(Long basketId, String storeName, int totalPrice, List<BasketMenuResponse> menus) {
        this.basketId = basketId;
        this.storeName = storeName;
        this.totalPrice = totalPrice;
        this.menus = menus;
    }
}
