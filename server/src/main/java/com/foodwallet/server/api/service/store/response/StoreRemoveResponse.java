package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.store.Store;
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

    public static StoreRemoveResponse of(Store store) {
        return StoreRemoveResponse.builder()
            .type(store.getType().getText())
            .name(store.getName())
            .removedDateTime(LocalDateTime.now())
            .build();
    }
}
