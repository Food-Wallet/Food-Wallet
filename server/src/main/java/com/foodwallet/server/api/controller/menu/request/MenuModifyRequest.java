package com.foodwallet.server.api.controller.menu.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuModifyRequest {

    private String name;
    private String description;
    private Integer price;

    @Builder
    private MenuModifyRequest(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
