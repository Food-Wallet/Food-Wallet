package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreCreateResponse {

    private final Long storeId;
    private final String storeType;
    private final String storeName;
    private final String storeDescription;
    private final String storeImage;
    private final LocalDateTime createdDateTime;

    @Builder
    private StoreCreateResponse(Long storeId, String storeType, String storeName, String storeDescription, String storeImage, LocalDateTime createdDateTime) {
        this.storeId = storeId;
        this.storeType = storeType;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.storeImage = storeImage;
        this.createdDateTime = createdDateTime;
    }

    public static StoreCreateResponse of(Store store) {
        return StoreCreateResponse.builder()
            .storeId(store.getId())
            .storeType(store.getType().getText())
            .storeName(store.getName())
            .storeDescription(store.getDescription())
            .storeImage(store.getImage() == null ? null : store.getImage().getStoreFileName())
            .createdDateTime(store.getCreatedDateTime())
            .build();
    }
}
