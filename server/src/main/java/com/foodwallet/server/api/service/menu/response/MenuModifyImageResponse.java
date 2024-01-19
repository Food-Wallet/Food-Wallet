package com.foodwallet.server.api.service.menu.response;

import com.foodwallet.server.domain.menu.Menu;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuModifyImageResponse {

    private final String name;
    private final LocalDateTime modifiedDateTime;

    @Builder
    private MenuModifyImageResponse(String name, LocalDateTime modifiedDateTime) {
        this.name = name;
        this.modifiedDateTime = modifiedDateTime;
    }

    public static MenuModifyImageResponse of(Menu menu) {
        return MenuModifyImageResponse.builder()
            .name(menu.getName())
            .modifiedDateTime(LocalDateTime.now())
            .build();
    }
}
