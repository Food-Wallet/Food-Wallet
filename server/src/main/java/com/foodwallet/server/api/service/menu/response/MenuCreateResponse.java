package com.foodwallet.server.api.service.menu.response;

import com.foodwallet.server.domain.menu.Menu;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuCreateResponse {

    private final String name;
    private final String description;
    private final int price;
    private final LocalDateTime createdDateTime;

    @Builder
    private MenuCreateResponse(String name, String description, int price, LocalDateTime createdDateTime) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdDateTime = createdDateTime;
    }

    public static MenuCreateResponse of(Menu menu) {
        return MenuCreateResponse.builder()
            .name(menu.getName())
            .description(menu.getDescription())
            .price(menu.getPrice())
            .createdDateTime(menu.getCreatedDateTime())
            .build();
    }
}
