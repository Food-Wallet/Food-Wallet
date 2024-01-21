package com.foodwallet.server.domain.bookmark.repository.response;

import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkResponse {

    private final Long storeId;
    private final String status;
    private final String type;
    private final String storeName;
    private final double avgRate;
    private final int reviewCount;
    private final String storeImageUrl;

    @Builder
    public BookmarkResponse(Long storeId, StoreStatus status, StoreType type, String storeName, double avgRate, int reviewCount, String storeImageUrl) {
        this.storeId = storeId;
        this.status = status.getText();
        this.type = type.getText();
        this.storeName = storeName;
        this.avgRate = avgRate;
        this.reviewCount = reviewCount;
        this.storeImageUrl = storeImageUrl;
    }
}
