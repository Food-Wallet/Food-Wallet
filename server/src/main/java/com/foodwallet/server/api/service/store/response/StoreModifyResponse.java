package com.foodwallet.server.api.service.store.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreModifyResponse {

    private final String type;
    private final String name;
    private final String description;
    private final LocalDateTime modifiedDateTime;

    @Builder
    private StoreModifyResponse(String type, String name, String description, LocalDateTime modifiedDateTime) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.modifiedDateTime = modifiedDateTime;
    }
}
