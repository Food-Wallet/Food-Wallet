package com.foodwallet.server.api.service.store.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreModifyImageResponse {

    private final String type;
    private final String name;
    private final LocalDateTime imageModifiedDateTime;

    @Builder
    private StoreModifyImageResponse(String type, String name, LocalDateTime imageModifiedDateTime) {
        this.type = type;
        this.name = name;
        this.imageModifiedDateTime = imageModifiedDateTime;
    }
}
