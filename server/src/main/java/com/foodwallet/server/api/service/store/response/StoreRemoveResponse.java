package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreRemoveResponse {

    private final Long storeId;
    private final String storeType;
    private final String storeName;
    private final LocalDateTime removedDateTime;

    @Builder
    private StoreRemoveResponse(Long storeId, String storeType, String storeName, LocalDateTime removedDateTime) {
        this.storeId = storeId;
        this.storeType = storeType;
        this.storeName = storeName;
        this.removedDateTime = removedDateTime;
    }

    public static StoreRemoveResponse of(Store store, LocalDateTime currentDateTime) {
        return StoreRemoveResponse.builder()
            .storeId(store.getId())
            .storeType(store.getType().getText())
            .storeName(store.getName())
            .removedDateTime(currentDateTime)
            .build();
    }
}