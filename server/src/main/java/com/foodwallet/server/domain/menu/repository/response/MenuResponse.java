package com.foodwallet.server.domain.menu.repository.response;

import com.foodwallet.server.domain.menu.SellingStatus;
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
    public MenuResponse(Long menuId, String name, String description, int price, SellingStatus status, String menuImage) {
        this.menuId = menuId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status.getText();
        this.menuImage = menuImage;
    }
}
