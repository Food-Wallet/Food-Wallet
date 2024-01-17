package com.foodwallet.server.api.service.menu.response;

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
}
