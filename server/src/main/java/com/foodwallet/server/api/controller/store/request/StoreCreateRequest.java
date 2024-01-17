package com.foodwallet.server.api.controller.store.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreCreateRequest {

    private String type;
    private String name;
    private String description;

    @Builder
    private StoreCreateRequest(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }
}
