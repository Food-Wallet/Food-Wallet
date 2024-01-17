package com.foodwallet.server.api.service.menu.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuResponse {

    private final Long menuId;
    private final String name;
    private final String description;
    private final int price;
    private final String status;
    private final String menuImage;

    @Builder
    private MenuResponse(Long menuId, String name, String description, int price, String status, String menuImage) {
        this.menuId = menuId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.menuImage = menuImage;
    }
}
