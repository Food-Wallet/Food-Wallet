package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreModifyInfoResponse {

    private final Long storeId;
    private final String storeType;
    private final String storeName;
    private final String storeDescription;
    private final LocalDateTime modifiedDateTime;

    @Builder
    private StoreModifyInfoResponse(Long storeId, String storeType, String storeName, String storeDescription, LocalDateTime modifiedDateTime) {
        this.storeId = storeId;
        this.storeType = storeType;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.modifiedDateTime = modifiedDateTime;
    }

    public static StoreModifyInfoResponse of(Store store) {
        return StoreModifyInfoResponse.builder()
            .storeId(store.getId())
            .storeType(store.getType().getText())
            .storeName(store.getName())
            .storeDescription(store.getDescription())
            .modifiedDateTime(LocalDateTime.now())
            .build();
    }
}
