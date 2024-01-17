package com.foodwallet.server.api.service.basket.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BasketMenuResponse {

    private final Long menuId;
    private final String menuName;
    private final String menuImage;
    private final int count;
    private final int totalPrice;

    @Builder
    private BasketMenuResponse(Long menuId, String menuName, String menuImage, int count, int totalPrice) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuImage = menuImage;
        this.count = count;
        this.totalPrice = totalPrice;
    }
}
