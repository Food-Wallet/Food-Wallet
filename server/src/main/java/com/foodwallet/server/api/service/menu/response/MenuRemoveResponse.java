package com.foodwallet.server.api.service.menu.response;

import com.foodwallet.server.domain.menu.Menu;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuRemoveResponse {

    private final String name;
    private final LocalDateTime removedDateTime;

    @Builder
    private MenuRemoveResponse(String name, LocalDateTime removedDateTime) {
        this.name = name;
        this.removedDateTime = removedDateTime;
    }

    public static MenuRemoveResponse of(Menu menu) {
        return MenuRemoveResponse.builder()
            .name(menu.getName())
            .removedDateTime(LocalDateTime.now())
            .build();
    }
}
