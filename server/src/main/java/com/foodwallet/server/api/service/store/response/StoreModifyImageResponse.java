package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreModifyImageResponse {

    private final Long storeId;
    private final String storeName;
    private final String storeImage;
    private final LocalDateTime modifiedDateTime;

    @Builder
    private StoreModifyImageResponse(Long storeId, String storeName, String storeImage, LocalDateTime modifiedDateTime) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeImage = storeImage;
        this.modifiedDateTime = modifiedDateTime;
    }

    public static StoreModifyImageResponse of(Store store) {
        return StoreModifyImageResponse.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .storeImage(store.getImage().getStoreFileName())
            .modifiedDateTime(LocalDateTime.now())
            .build();
    }
}
