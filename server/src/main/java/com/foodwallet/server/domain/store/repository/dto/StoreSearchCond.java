package com.foodwallet.server.domain.store.repository.dto;

import com.foodwallet.server.domain.store.StoreType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreSearchCond {

    private final StoreType type;
    private final String query;

    @Builder
    private StoreSearchCond(StoreType type, String query) {
        this.type = type;
        this.query = query;
    }
}
