package com.foodwallet.server.api.service.basket.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BasketCreateResponse {

    private final String menuName;
    private final int count;
    private final int totalPrice;

    @Builder
    private BasketCreateResponse(String menuName, int count, int totalPrice) {
        this.menuName = menuName;
        this.count = count;
        this.totalPrice = totalPrice;
    }
}
