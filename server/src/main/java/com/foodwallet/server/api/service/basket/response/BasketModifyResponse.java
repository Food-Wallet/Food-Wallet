package com.foodwallet.server.api.service.basket.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BasketModifyResponse {

    private final String menuName;
    private final int count;
    private final int totalPrice;

    @Builder
    private BasketModifyResponse(String menuName, int count, int totalPrice) {
        this.menuName = menuName;
        this.count = count;
        this.totalPrice = totalPrice;
    }
}
