package com.foodwallet.server.api.service.menu.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuModifyServiceRequest {

    private final String name;
    private final String description;
    private final int price;

    @Builder
    private MenuModifyServiceRequest(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
