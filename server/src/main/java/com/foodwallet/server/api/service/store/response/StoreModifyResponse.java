package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.store.Store;
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

    public static StoreModifyResponse of(Store store) {
        return StoreModifyResponse.builder()
            .type(store.getType().getText())
            .name(store.getName())
            .description(store.getDescription())
            .modifiedDateTime(LocalDateTime.now())
            .build();
    }
}
