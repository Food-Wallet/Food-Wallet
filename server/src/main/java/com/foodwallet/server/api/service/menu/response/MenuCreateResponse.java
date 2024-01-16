package com.foodwallet.server.api.service.menu.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuCreateResponse {

    private final String name;
    private final String description;
    private final Integer price;
    private final LocalDateTime createdDateTime;

    @Builder
    private MenuCreateResponse(String name, String description, Integer price, LocalDateTime createdDateTime) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdDateTime = createdDateTime;
    }
}
