package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreCreateResponse {

    private final String type;
    private final String name;
    private final String description;
    private final LocalDateTime createdDateTime;

    @Builder
    private StoreCreateResponse(String type, String name, String description, LocalDateTime createdDateTime) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.createdDateTime = createdDateTime;
    }

    public static StoreCreateResponse of(Store store) {
        return StoreCreateResponse.builder()
            .type(store.getType().getText())
            .name(store.getName())
            .description(store.getDescription())
            .createdDateTime(store.getCreatedDateTime())
            .build();
    }
}
