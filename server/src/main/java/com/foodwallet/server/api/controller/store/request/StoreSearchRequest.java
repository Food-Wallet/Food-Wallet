package com.foodwallet.server.api.controller.store.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreSearchRequest {

    private String type;
    private String query;
    private Integer page;

    @Builder
    private StoreSearchRequest(String type, String query, Integer page) {
        this.type = type;
        this.query = query;
        this.page = page;
    }
}
