package com.foodwallet.server.api.service.menu.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuModifyStatusResponse {

    private final String name;
    private final String status;
    private final LocalDateTime modifiedDateTime;

    @Builder
    private MenuModifyStatusResponse(String name, String status, LocalDateTime modifiedDateTime) {
        this.name = name;
        this.status = status;
        this.modifiedDateTime = modifiedDateTime;
    }
}
