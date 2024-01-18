package com.foodwallet.server.api.service.store.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreModifyServiceRequest {

    private final String type;
    private final String name;
    private final String description;

    @Builder
    private StoreModifyServiceRequest(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }
}
