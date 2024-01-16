package com.foodwallet.server.api.service.menu.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuModifyResponse {

    private final String name;
    private final String description;
    private final Integer price;
    private final LocalDateTime modifiedDateTime;

    @Builder
    private MenuModifyResponse(String name, String description, Integer price, LocalDateTime modifiedDateTime) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.modifiedDateTime = modifiedDateTime;
    }
}
