package com.foodwallet.server.api.service.menu.response;

import com.foodwallet.server.domain.menu.Menu;
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

    public static MenuModifyResponse of(Menu menu) {
        return MenuModifyResponse.builder()
            .name(menu.getName())
            .description(menu.getDescription())
            .price(menu.getPrice())
            .modifiedDateTime(LocalDateTime.now())
            .build();
    }
}
