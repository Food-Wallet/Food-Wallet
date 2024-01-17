package com.foodwallet.server.api.service.order.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderMenuResponse {

    private final String menuName;
    private final String menuImage;
    private final int orderPrice;
    private final int count;

    @Builder
    private OrderMenuResponse(String menuName, String menuImage, int orderPrice, int count) {
        this.menuName = menuName;
        this.menuImage = menuImage;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
