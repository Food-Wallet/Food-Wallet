package com.foodwallet.server.api.service.store.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreRemoveResponse {

    private final String type;
    private final String name;
    private final LocalDateTime removedDateTime;

    @Builder
    private StoreRemoveResponse(String type, String name, LocalDateTime removedDateTime) {
        this.type = type;
        this.name = name;
        this.removedDateTime = removedDateTime;
    }
}
